package tw.edu.ntu.lowerbound10hours.jlaskhibernate;

import static org.testng.Assert.assertEquals;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tw.edu.ntu.lowerbound10hours.jlask.Jlask;

public class DatabaseOperationTest {

  private Hibernate db = new Hibernate();

  @BeforeClass
  public void beforeClass() {
    db.drop_all();
    db.init_app(new Jlask());
  }

  @Test
  public void testInsert() {
    Post post = new Post("Test Post1", "It's a testing");
    db.beginTransaction();
    db.getSession().save(post);
    db.getTransaction().commit();
  }

  @Test(dependsOnMethods = {"testInsert"})
  public void testSearch() {
    Criteria criteria = db.getSession().createCriteria(Post.class);
    List results = criteria.list();
    String rv = "Test Post1";
    Post post = (Post) results.get(0);
    assertEquals(post.getTitle(), rv);
  }

  @Test(dependsOnMethods = {"testSearch"})
  public void testDelete() {
    Criteria criteria1 = db.getSession().createCriteria(Post.class);
    Post post = (Post) criteria1.add(Restrictions.eq("id", 1)).list().get(0);
    db.beginTransaction();
    db.getSession().delete(post);
    db.getTransaction().commit();

    Criteria criteria2 = db.getSession().createCriteria(Post.class);
    assertEquals(criteria2.list().size(), 0);
  }

  @AfterClass
  public void afterClass() {
    db.getSession().close();
  }
}
