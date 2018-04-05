package com.g4w18.unittests;

import com.g4w18.controllers.ClientJpaController;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.custombeans.AuthorWithTotalSales;
import com.g4w18.custombeans.BookWithTotalSales;
import com.g4w18.custombeans.ClientWithTotalSales;
import com.g4w18.custombeans.PublisherWithTotalSales;
import com.g4w18.customcontrollers.CustomClientController;
import com.g4w18.customcontrollers.CustomInvoiceDetailController;
import com.g4w18.customcontrollers.CustomMasterInvoiceController;
import com.g4w18.customcontrollers.ReportQueries;
import com.g4w18.entities.Client;
import com.g4w18.entities.InvoiceDetail;
import com.g4w18.entities.MasterInvoice;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests the custom query methods of several JPA controller classes.
 * The custom query methods that will be tested come from the following JPA controller classes:
 - CustomClientController
 - CustomInvoiceDetailController
 - CustomMasterInvoiceController
 - ReportQueries
 * 
 * @author Marc-Daniel
 */
@Ignore
@RunWith(Arquillian.class)
public class CustomJpaQueryTester 
{
    private Logger logger = Logger.getLogger(CustomJpaQueryTester.class.getName());
    
    @Resource(name = "java:app/jdbc/TheBooktopia")
    private DataSource dataStore;
    
    @Inject private CustomClientController clientJpaController;
    @Inject private CustomInvoiceDetailController invoiceDetailJpaController;
    @Inject private CustomMasterInvoiceController masterInvoiceJpaController;
    @Inject private ReportQueries reportQueries;
    
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
            .addPackage(CustomClientController.class.getPackage()).addPackage(ClientJpaController.class.getPackage())
            .addPackage(RollbackFailureException.class.getPackage())
            .addPackage(Client.class.getPackage())
            .addPackage(AuthorWithTotalSales.class.getPackage())
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

        try (Connection connection = dataStore.getConnection()) 
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
     * Tests the 'findClientByUsername' method of the custom Client JPA controller class.
     */
    @Test
    public void testFindClientByUsername()
    {
        Client marcdanielClient = clientJpaController.findClientByUsername("mdaniels");
        
        // The full name of the Client record with the username 'mdaniels' should be 'Marc-Daniel Dialogo'.
        assertThat(marcdanielClient.getFirstName() + " " + marcdanielClient.getLastName()).isEqualTo("Marc-Daniel Dialogo");
    }
    
    /**
     * Tests the 'findClientByEmail' method of the custom Client JPA controller class.
     */
    @Test
    public void testFindClientByEmail()
    {
        Client dawsonConsumerClient = clientJpaController.findClientByEmail("cst.send@gmail.com");
        
        // The ID of the Client record with the email address 'cst.send@gmail.com' should be '1'.
        assertThat(dawsonConsumerClient.getClientId()).isEqualTo(1);
    }
    
    /**
     * Tests the 'findClientByCredentials' method of the custom Client JPA controller class.
     */
    @Test
    public void testFindClientByCredentials()
    {
        Client acutchieClient = clientJpaController.findClientByCredentials("acutchie0", "B5eazih");
        
        // The ID of the Client record with username 'acutchie0' and password 'B5eazih' should be '7'.
        assertThat(acutchieClient.getClientId()).isEqualTo(7);
    }
    
    /**
     * Tests the 'findInvoicesByMasterInvoice' method of the custom Invoice Detail JPA controller class.
     */
    @Test
    public void testFindInvoicesByMasterInvoice()
    {
        MasterInvoice masterInvoiceWithId1 = masterInvoiceJpaController.findMasterInvoice(1);
        List<InvoiceDetail> invoiceDetailsForMasterInvoiceId1 = invoiceDetailJpaController.findInvoicesByMasterInvoice(masterInvoiceWithId1);
        
        // There should be 4 invoice details entries for the master invoice record with ID of '1'.
        assertThat(invoiceDetailsForMasterInvoiceId1).hasSize(4);
    }
    
    /**
     * Tests the 'findBooksWithTotalSalesBetweenDates' method of the report queries class.
     */
    @Test
    public void testFindBooksWithTotalSalesBetweenDates()
    {
        LocalDateTime firstDate = LocalDateTime.of(2018, Month.FEBRUARY, 1, 0, 0);
        LocalDateTime secondDate = LocalDateTime.of(2018, Month.FEBRUARY, 2, 0, 0);  
        Date date1 = Date.from(firstDate.atZone(ZoneId.systemDefault()).toInstant());
        Date date2 = Date.from(secondDate.atZone(ZoneId.systemDefault()).toInstant());
        
        List<BookWithTotalSales> booksWithTotalSales = reportQueries.findBooksWithTotalSalesBetweenDates(date1, date2);
        
        // Between the dates of February 1 2018 and February 2 2018, 4 books have been sold.
        assertThat(booksWithTotalSales).hasSize(4);
    }
    
    /**
     * Tests the 'findClientsWithTotalSalesBetweenDates' method of the report queries class.
     */
    @Test
    public void testFindClientsWithTotalSalesBetweenDates()
    {
        LocalDateTime firstDate = LocalDateTime.of(2018, Month.FEBRUARY, 1, 0, 0);
        LocalDateTime secondDate = LocalDateTime.of(2018, Month.FEBRUARY, 2, 0, 0);  
        Date date1 = Date.from(firstDate.atZone(ZoneId.systemDefault()).toInstant());
        Date date2 = Date.from(secondDate.atZone(ZoneId.systemDefault()).toInstant());
        
        List<ClientWithTotalSales> clientsWithTotalSales = reportQueries.findClientsWithTotalSalesBetweenDates(date1, date2);
        
        // Between the dates of February 1 2018 and February 2 2018, 1 client has purchased books.
        assertThat(clientsWithTotalSales).hasSize(1);
    }
    
    /**
     * Tests the 'findAuthorsWithTotalSalesBetweenDates' method of the report queries class.
     */
    @Test
    public void testFindAuthorsWithTotalSalesBetweenDates()
    {
        LocalDateTime firstDate = LocalDateTime.of(2018, Month.FEBRUARY, 1, 0, 0);
        LocalDateTime secondDate = LocalDateTime.of(2018, Month.FEBRUARY, 2, 0, 0);  
        Date date1 = Date.from(firstDate.atZone(ZoneId.systemDefault()).toInstant());
        Date date2 = Date.from(secondDate.atZone(ZoneId.systemDefault()).toInstant());
        
        List<AuthorWithTotalSales> authorsWithTotalSales = reportQueries.findAuthorsWithTotalSalesBetweenDates(date1, date2);
        
        // Between the dates of February 1 2018 and February 2 2018, 4 different authors had at least one of their books sold.
        assertThat(authorsWithTotalSales).hasSize(4);
    }
    
    /**
     * Tests the 'findPublishersWithTotalSalesBetweenDates' method of the report queries class.
     */
    @Test
    public void testFindPublishersWithTotalSalesBetweenDates()
    {
        LocalDateTime firstDate = LocalDateTime.of(2018, Month.FEBRUARY, 1, 0, 0);
        LocalDateTime secondDate = LocalDateTime.of(2018, Month.FEBRUARY, 2, 0, 0);  
        Date date1 = Date.from(firstDate.atZone(ZoneId.systemDefault()).toInstant());
        Date date2 = Date.from(secondDate.atZone(ZoneId.systemDefault()).toInstant());
        
        List<PublisherWithTotalSales> publishersWithTotalSales = reportQueries.findPublishersWithTotalSalesBetweenDates(date1, date2); 
        
        // Between the dates of February 1 2018 and February 2 2018, 4 different publishers had at least one of their books sold.
        assertThat(publishersWithTotalSales).hasSize(4);
    }
}
