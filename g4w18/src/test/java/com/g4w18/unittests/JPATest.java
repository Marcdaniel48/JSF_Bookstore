package com.g4w18.unittests;

import com.g4w18.controllers.AuthorJpaController;
import com.g4w18.controllers.BookJpaController;
import com.g4w18.customcontrollers.CustomAuthorController;
import com.g4w18.customcontrollers.CustomBookController;
import com.g4w18.controllers.exceptions.IllegalOrphanException;
import com.g4w18.entities.Author;
import com.g4w18.entities.Book;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.TypedQuery;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Ignore;

/**
 * Testing for JPA methods. 
 * 
 * @author Salman Haidar
 */
@RunWith(Arquillian.class)
public class JPATest {

    
        @Deployment
    public static WebArchive deploy() {

        // Use an alternative to the JUnit assert library called AssertJ
        // Need to reference MySQL driver as it is not part of either
        // embedded or remote
        final File[] dependencies = Maven
                .resolver()
                .loadPomFromFile("pom.xml")
                .resolve("mysql:mysql-connector-java",
                        "org.assertj:assertj-core").withoutTransitivity()
                .asFile();

        // The webArchive is the special packaging of your project
        // so that only the test cases run on the server or embedded
        // container
        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                .addPackage(CustomBookController.class.getPackage())
                .addPackage(AuthorJpaController.class.getPackage())
                .addPackage(IllegalOrphanException.class.getPackage())
                .addPackage(Book.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/glassfish-resources.xml"), "glassfish-resources.xml")
                .addAsResource(new File("src/main/resources/META-INF/persistence.xml"), "META-INF/persistence.xml")
                .addAsResource("createBookstoreTables.sql")
                .addAsLibraries(dependencies);

        return webArchive;
    }
    
    
    private Logger logger = Logger.getLogger(JPATest.class.getName());
    
    @Inject
    private CustomBookController bookJpaController;
    
    @Inject
    private CustomAuthorController aJc;
    
    @Resource(name = "java:app/jdbc/TheBooktopia")
    private DataSource ds;
    
    /**
     * Find all books in DB.
     */
    @Test
    public void should_find_all_books() throws SQLException{
        List<Book> lb = bookJpaController.findBookEntities();
        
        logger.log(Level.INFO,"Data>>>{0}",lb.get(0).getTitle());
        
        assertThat(lb).hasSize(100);
        
    }
    
    /**
     * Find a book with its title provided.
     * @throws SQLException 
     */
    @Test
    public void find_book_with_specific_name() throws SQLException{
        
        List<Book> specificBook = bookJpaController.findBooksByTitleSpecific("The Silmarillion");
        logger.log(Level.INFO,"Data>>SPECIFIC TITLE>{0}"+specificBook.get(0).getTitle());
        
        assertThat(specificBook).hasSize(1);
    }
    
    /**
     * Find author with general term.
     * @throws SQLException 
     */
    @Test
    public void find_author_with_general_name() throws SQLException{
        
        List<Author> findAuthorByName = aJc.findAuthor("C.S.%");
        
        
        logger.log(Level.INFO,"AUTHORNAME Data>>>{0}"+ findAuthorByName.get(0).getFirstName());
        
        assertThat(findAuthorByName).hasSize(1);
    } 
    
    /**
     * Find books with title with one letter provided by asc ordering.
     * @throws SQLException 
     */
    @Test
    public void find_book_with_general_title_by_ascending() throws SQLException{
        
        List<Book> specificBook = bookJpaController.findBooksByTitle("c%");
        
        
        for(int i = 0;i<specificBook.size();i++)
            logger.log(Level.INFO,"Data>>>{0}"+specificBook.get(i).getTitle() + "---------");
          
        assertThat(specificBook).hasSize(4);
    } 
    
    /**
     * Find publisher with with one letter provided by asc ordering.
     * @throws SQLException 
     */
    @Test
    public void find_book_with_general_publisher_by_ascending() throws SQLException{
        
        List<Book> specificBook = bookJpaController.findLikePublisher("v%"); 
                 
        assertThat(specificBook).hasSize(4);
    } 
    /**
     * Get all books from each author found from the query
     * @throws SQLException 
     */
    @Test
    public void get_author_books() throws SQLException
    {
        List<Author> authorList = aJc.findAuthor("c%");
        List<Book> allBooks = new ArrayList<Book>();
        
        int authorCount = authorList.size();
        
        logger.log(Level.INFO,"---------==HOW MANY AUTHORS WITH c======-----"+  authorCount);
        
        
        for(int i=0;i< authorCount;i++)
        {
            allBooks.addAll(authorList.get(i).getBookList());
        }
        
        assertThat(allBooks).hasSize(7);
    }
    
    
    
    
    
    
    /////////////////For Database ///////////
    /**
     * This routine is courtesy of Bartosz Majsak who also solved my Arquillian
     * remote server problem
     */
    
    @Before
    public void seedDatabase() {
        final String seedDataScript = loadAsString("createBookstoreTables.sql");

        try (Connection connection = ds.getConnection()) {
            for (String statement : splitStatements(new StringReader(
                    seedDataScript), ";")) {
                connection.prepareStatement(statement).execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed seeding database", e);
        }
        logger.log(Level.INFO, "Successful seeding.");
    }

    /**
     * The following methods support the seedDatabse method
     */
    private String loadAsString(final String path) {
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(path)) {
            return new Scanner(inputStream).useDelimiter("\\A").next();
        } catch (IOException e) {
            throw new RuntimeException("Unable to close input stream.", e);
        }
    }

    private List<String> splitStatements(Reader reader,
            String statementDelimiter) {
        final BufferedReader bufferedReader = new BufferedReader(reader);
        final StringBuilder sqlStatement = new StringBuilder();
        final List<String> statements = new LinkedList<>();
        try {
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || isComment(line)) {
                    continue;
                }
                sqlStatement.append(line);
                if (line.endsWith(statementDelimiter)) {
                    statements.add(sqlStatement.toString());
                    sqlStatement.setLength(0);
                }
            }
            return statements;
        } catch (IOException e) {
            throw new RuntimeException("Failed parsing sql", e);
        }
    }

    private boolean isComment(final String line) {
        return line.startsWith("--") || line.startsWith("//")
                || line.startsWith("/*");
    }
    

    
    
}
