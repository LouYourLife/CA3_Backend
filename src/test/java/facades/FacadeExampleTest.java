package facades;

import dto.MovieDTO;
import entities.Movie;
import utils.EMF_Creator;
import entities.RenameMe;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class FacadeExampleTest {

    private static EntityManagerFactory emf;
    private static FacadeExample facade1;
    private static FetchFacade facade;

    public FacadeExampleTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade1 = FacadeExample.getFacadeExample(emf);
       facade = new FetchFacade();
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.createQuery("DELETE from Movie").executeUpdate();
            String[] testChar1 = {"Character1","Character2"};
            String[] testChar2 = {"Character1","Character2","Character3"};
            em.persist(new Movie("Title1", 1, "Opening crawl something something","director1","producer1","2020-01-01", testChar1));
            em.persist(new Movie("Title2", 2, "Opening crawl about space","director2","producer2","2020-02-02", testChar2));

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }
    
    @Test
    public void testFetchParallel() throws InterruptedException, ExecutionException {
        List<String> list = facade.fetchParallel();
        assertEquals(6, list.size());
    }

}
