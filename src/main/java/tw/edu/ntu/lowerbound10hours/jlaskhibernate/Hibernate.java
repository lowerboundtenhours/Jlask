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

  /** Get the session from the sessionFactory. */
  public Session createScopedSession() {
    this.createSession();
    Session session = this.factory.openSession();
    return session;
  }

  /** Create the sessionFactory based on the configuration. */
  public void createSession() {
    try {
      this.factory = this.config.buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Failed to create sessionFactory object." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  /** Initialize the Hibernate object. */
  public void initApp(Jlask app) {
    this.app = app;
    this.session = this.createScopedSession();
  }

  /** Set the configuration to drop all databases when created. */
  public void dropAll() {
    try {
      this.config.setProperty("hibernate.hbm2ddl.auto", "create-drop");
    } catch (HibernateException e) {
      e.printStackTrace();
    }
  }

  /** Set the transaction to handle the following operation. */
  public void beginTransaction() {
    this.tx = this.session.beginTransaction();
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
