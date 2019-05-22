package tw.edu.ntu.lowerbound10hours.jlaskhibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import tw.edu.ntu.lowerbound10hours.jlask.Jlask;

public class Hibernate {
  private SessionFactory factory = null;
  private Session session = null;
  private Transaction tx = null;
  private Configuration config = null;
  private Jlask app = null;

  public Hibernate() {
    this.config = new Configuration().configure();
  }

  public Hibernate(Jlask app) {
    this.app = app;
    this.config = new Configuration().configure();
  }

  public Session create_scoped_session() {
    this.create_session();
    Session session = this.factory.openSession();
    return session;
  }

  public void create_session() {
    try {
      this.factory = this.config.buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Failed to create sessionFactory object." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  public void make_declarative_base() {}

  public void init_app(Jlask app) {
    this.app = app;
    this.session = this.create_scoped_session();
    this.tx = session.beginTransaction();
  }

  private void execute_for_all_tables() {}

  public void create_all() {}

  public void drop_all() {
    try {
      this.config.setProperty("hibernate.hbm2ddl.auto", "create-drop");
    } catch (HibernateException e) {
      e.printStackTrace();
    }
  }

  public SessionFactory getSessionFactory() {
    return this.factory;
  }

  public Session getSession() {
    return this.session;
  }

  public Transaction getTransaction() {
    return this.tx;
  }
}
