package tw.edu.ntu.lowerbound10hours.jlaskhibernate;

import static org.testng.Assert.assertEquals;

import java.util.List;
import org.hibernate.Criteria;
import org.testng.annotations.Test;
import tw.edu.ntu.lowerbound10hours.jlask.Jlask;

public class DatabaseOperationTest {

  private Hibernate db = new Hibernate();

  public DatabaseOperationTest() {
    db.drop_all();
    db.init_app(new Jlask());
  }

  @Test
  public void testInsert() {
    Post post = new Post("Test Post1", "It's a testing");
    db.getSession().save(post);
    db.getTransaction().commit();
  }

  @Test
  public void testSearch() {
    Criteria criteria = db.getSession().createCriteria(Post.class);
    List results = criteria.list();
    String rv = "Test Post1";
    Post post = (Post) results.get(0);
    assertEquals(post.getTitle(), rv);
  }
}
