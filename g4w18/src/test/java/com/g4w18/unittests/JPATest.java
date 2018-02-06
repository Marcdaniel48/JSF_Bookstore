package com.g4w18.unittests;

import com.g4w18.controllers.AuthorJpaController;
import com.g4w18.controllers.BookJpaController;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testing for JPA methods. 
 * 
 * @author Salman Haidar
 */
@RunWith(Arquillian.class)
public class JPATest {
     
    private Logger logger = Logger.getLogger(JPATest.class.getName());
    
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
    
    @Inject
    private BookJpaController bj;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Resource(name = "java:app/jdbc/TheBooktopia")
    private DataSource ds;
    
    /**
     * Find all books in DB.
     */
    @Test
    public void should_find_all_books() throws SQLException{
        List<Book> lb = bj.findBookEntities();
        
        logger.log(Level.INFO,"Data>>>{0}",lb.get(0).getTitle());
        
        assertThat(lb).hasSize(100);
        
    }
    
    
    /**
     * Same test as the one above, but trying with typed query.
     * @throws SQLException 
     */
    @Test
    public void find_books_with_typed_query() throws SQLException{
        
        TypedQuery<Book> query = entityManager.createQuery("Select b from Book b",Book.class);
        
        List<Book> book = query.getResultList();
        logger.log(Level.INFO,"Data>>>{0}",book.get(0).getTitle());
        
        assertThat(book).hasSize(100);
    }
    
    /**
     * Find a book with its title provided.
     * @throws SQLException 
     */
    @Test
    public void find_book_with_specific_name() throws SQLException{
        
        List specificBook = entityManager.createQuery("Select b from Book b where b.title = ?1")
                .setParameter(1, "The Silmarillion")
                .getResultList();
        
        
        logger.log(Level.INFO,"Data>>>{0}",specificBook.get(0));
        
        assertThat(specificBook).hasSize(1);
    }
    
    /**
     * Find all the books for a specific genre.
     * @throws SQLException 
     */
    @Test
    public void find_book_with_specific_genres() throws SQLException{
        
        List specificBook = entityManager.createQuery("Select b from Book b where b.genre = ?1")
                .setParameter(1, "Fantasy")
                .getResultList();
        
        
        logger.log(Level.INFO,"Data>>>{0}",specificBook.get(0));
        
        assertThat(specificBook).hasSize(20);
    } 
    
    /**
     * Find all the books from a specific publisher.
     * @throws SQLException 
     */
    @Test
    public void find_book_with_specific_publisher() throws SQLException{
        
        List specificBook = entityManager.createQuery("Select b from Book b where b.publisher = ?1")
                .setParameter(1, "HarperCollins")
                .getResultList();
        
        
        logger.log(Level.INFO,"Data>>>{0}",specificBook.get(0));
        
        assertThat(specificBook).hasSize(6);
    } 
    
    /**
     * Find a book with the help of an isbn.
     * @throws SQLException 
     */
    @Test
    public void find_book_with_specific_isbn() throws SQLException{
        
        List specificBook = entityManager.createQuery("Select b from Book b where b.isbnNumber = ?1")
                .setParameter(1, "978-0261102736")
                .getResultList();
        
        
        logger.log(Level.INFO,"Data>>>{0}",specificBook.get(0));
        
        assertThat(specificBook).hasSize(1);
    } 
    
    /**
     * Find books with title with one letter provided.
     * @throws SQLException 
     */
    @Test
    public void find_book_with_general_title() throws SQLException{
        
        List specificBook = entityManager.createQuery("Select b from Book b where b.title LIKE ?1")
                .setParameter(1, "c%")
                .getResultList();
        
        
        logger.log(Level.INFO,"Data>>>{0}",specificBook.get(0));
        
        assertThat(specificBook).hasSize(4);
    }
    
    /**
     * Find books with title with one letter provided by asc ordering.
     * @throws SQLException 
     */
    @Test
    public void find_book_with_general_title_by_ascending() throws SQLException{
        
        List<Book> specificBook = entityManager.createQuery("Select b from Book b where b.title LIKE ?1 order by b.title asc")
                .setParameter(1, "c%")
                .getResultList();
        
        for(int i = 0;i<specificBook.size();i++)
            logger.log(Level.INFO,"Data>>>{0}",specificBook.get(i).getTitle() + "---------");
        
        assertThat(specificBook).hasSize(4);
    } 
}
