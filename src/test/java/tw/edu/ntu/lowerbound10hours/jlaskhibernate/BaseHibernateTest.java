package tw.edu.ntu.lowerbound10hours.jlaskhibernate;

import org.testng.annotations.Test;
import tw.edu.ntu.lowerbound10hours.jlask.Jlask;

public class BaseHibernateTest {

  @Test
  public void testBaseHibernate() {
    Hibernate db = new Hibernate();
    db.init_app(new Jlask());
  }
}
