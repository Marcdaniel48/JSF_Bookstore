/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.unittests;

import com.g4w18.controllers.ClientJpaController;
import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.entities.Client;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;
import static org.assertj.core.api.Assertions.assertThat;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests the methods of the Client JPA Controller
 * @author Marc-Daniel
 */
@RunWith(Arquillian.class)
public class ClientJpaTester
{
    private Logger logger = Logger.getLogger(ClientJpaTester.class.getName());
    
    @Inject
    ClientJpaController clientJpaController;
    
    @Resource(name = "java:app/jdbc/TheBooktopia")
    private DataSource dataSource;
    
    @Deployment
    public static WebArchive deploy()
    {

        // Use an alternative to the JUnit assert library called AssertJ
        // Need to reference MySQL driver as it is not part of either
        // embedded or remote
        final File[] dependencies = Maven.resolver().loadPomFromFile("pom.xml").resolve("mysql:mysql-connector-java", "org.assertj:assertj-core").withoutTransitivity().asFile(); 

        // The webArchive is the special packaging of your project
        // so that only the test cases run on the server or embedded
        // container
        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
            .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
            .addPackage(ClientJpaController.class.getPackage())
            .addPackage(RollbackFailureException.class.getPackage())
            .addPackage(Client.class.getPackage())
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsWebInfResource(new File("src/main/webapp/WEB-INF/glassfish-resources.xml"), "glassfish-resources.xml")
            .addAsResource(new File("src/main/resources/META-INF/persistence.xml"), "META-INF/persistence.xml")
            .addAsResource("createBookstoreTables.sql")
            .addAsLibraries(dependencies);

        return webArchive;
    }
    
    /**
     * This routine is courtesy of Bartosz Majsak who also solved my Arquillian
     * remote server problem
     */
    @Before
    public void seedDatabase() 
    {
        final String seedDataScript = loadAsString("createBookstoreTables.sql");

        try (Connection connection = dataSource.getConnection()) 
        {
            for (String statement : splitStatements(new StringReader(seedDataScript), ";")) 
            {
                connection.prepareStatement(statement).execute();
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            throw new RuntimeException("Failed seeding database", e);
        }
        
        logger.log(Level.INFO, "Successful seeding.");
    }

    /**
     * The following methods support the seedDatabse method
     */
    private String loadAsString(final String path) 
    {
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(path)) 
        {
            return new Scanner(inputStream).useDelimiter("\\A").next();
        } catch (IOException e) {
            throw new RuntimeException("Unable to close input stream.", e);
        }
    }

    private List<String> splitStatements(Reader reader, String statementDelimiter) 
    {
        final BufferedReader bufferedReader = new BufferedReader(reader);
        final StringBuilder sqlStatement = new StringBuilder();
        final List<String> statements = new LinkedList<>();
        
        try 
        {
            String line = "";
            
            while ((line = bufferedReader.readLine()) != null) 
            {
                line = line.trim();
                
                if (line.isEmpty() || isComment(line)) 
                {
                    continue;
                }
                
                sqlStatement.append(line);
                
                if (line.endsWith(statementDelimiter)) 
                {
                    statements.add(sqlStatement.toString());
                    sqlStatement.setLength(0);
                }
            }
            
            return statements;
        } 
        catch (IOException e) 
        {
            throw new RuntimeException("Failed parsing sql", e);
        }
    }

    private boolean isComment(final String line) 
    {
        return line.startsWith("--") || line.startsWith("//") || line.startsWith("/*");
    }
    
    /**
     * Should find all clients that were inserted through the createBookstoreTables.sql file
     * @throws SQLException
     */
    @Test
    public void find_all_clients() throws SQLException {
        List<Client> clients = clientJpaController.findClientEntities();
        
        assertThat(clients).hasSize(31);
    }
    
    /**
     * Creates a new Client entry into the database's Client table
     * @throws SQLException
     
    @Test
    public void create_new_client() throws SQLException, Exception, Exception {
        Client client = new Client(999, "SuperSpecialUser", "SuperSecretPassword123", "KingEmperor", "SpecialFirstName", "SpecialLastName", "SpecialCompany", "Addr1", "Addr2", "SpecialCity", "SpecialCountry", "00", "123123", "1234567890", "1234567890", "specialemail@superemail.com", false);
        clientJpaController.create(client);
        
        Client databaseClient = clientJpaController.findClient(999);
        
        assertThat(databaseClient.getPassword()).isEqualTo("SuperSecretPassword123");
    }*/
    
    /**
     * Should find single clients based on unique usernames
     * @throws SQLException
     */
    @Test
    public void find_clients_by_username() throws SQLException 
    {
        List<Client> clients = new ArrayList<>(); 
        clients.add(clientJpaController.findClientByUsername("DawsonConsumer"));
        clients.add(clientJpaController.findClientByUsername("DawsonManager"));
        clients.add(clientJpaController.findClientByUsername("mdaniels"));
        clients.add(clientJpaController.findClientByUsername("bwillbourne4"));
        clients.add(clientJpaController.findClientByUsername("lcuzenf"));
        
        assertThat(clients).hasSize(5);
    }
    
    /**
     * Should find a single client based on a unique id
     * @throws SQLException
     */
    @Test
    public void find_client_by_id() throws SQLException 
    {
        Client client = clientJpaController.findClient(3);
        
        assertThat(client.getUsername()).isEqualTo("sramirez");
    }
    
    /**
     * Should delete a single client based on a unique id. 
     * Trying to retrieve that deleted record will return null.
     * @throws SQLException
     */
    @Test
    public void destroy_client_by_id() throws SQLException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        clientJpaController.destroy(1);
        Client client = clientJpaController.findClient(1);
        
        assertThat(client).isNull();
    }
    
    /**
     * Should edit the username of a currently existing client
     * @throws SQLException
     */
    @Test
    public void edit_client_by_id() throws SQLException, NonexistentEntityException, RollbackFailureException, Exception 
    {
        // The current username for the client with id 4 should be mdaniels
        Client client = clientJpaController.findClient(4);
        client.setUsername("Raindr0p");
        
        clientJpaController.edit(client);
        Client databaseClient = clientJpaController.findClient(4);
        
        assertThat(databaseClient.getUsername()).isEqualTo("Raindr0p");
    }
    
    /**
     * Should find single clients based on unique email address
     * @throws SQLException
     */
    @Test
    public void find_clients_by_email() throws SQLException 
    {
        List<Client> clients = new ArrayList<>(); 
        clients.add(clientJpaController.findClientByEmail("cst.send@gmail.com"));
        clients.add(clientJpaController.findClientByEmail("sramirezdawson2017@gmail.com"));
        clients.add(clientJpaController.findClientByEmail("mmartineau3@java.com"));
        clients.add(clientJpaController.findClientByEmail("lcuzenf@google.co.uk"));
        clients.add(clientJpaController.findClientByEmail("gemanuelek@hostgator.com"));
        
        assertThat(clients).hasSize(5);
    }
    
    /**
     * Should find single clients based on their unique username and password combinations
     * @throws SQLException
     */
    @Test
    public void find_clients_by_credentials() throws SQLException 
    {
        List<Client> clients = new ArrayList<>(); 
        clients.add(clientJpaController.findClientByCredentials("jlouis", "booktopia"));
        clients.add(clientJpaController.findClientByCredentials("DawsonManager", "collegedawson"));
        clients.add(clientJpaController.findClientByCredentials("msedcole8", "oIQoG9GVRag7"));
        clients.add(clientJpaController.findClientByCredentials("lcuzenf", "lZUkixT"));
        clients.add(clientJpaController.findClientByCredentials("dearyj", "z1XFlx4"));
        
        assertThat(clients).hasSize(5);
    }
    
    
}
