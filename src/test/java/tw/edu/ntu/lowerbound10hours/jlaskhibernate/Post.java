package tw.edu.ntu.lowerbound10hours.jlaskhibernate;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "post")
public class Post {

  /** Used for testing database operation. */
  public Post(String title, String text) {
    this.title = title;
    this.text = text;
    this.date = new Date();
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "title")
  private String title;

  @Column(name = "text")
  private String text;

  @Column(name = "done")
  private boolean done;

  @Column(name = "pubDate")
  private Date date;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return "Post [id=" + id + "\ntitle=" + title + "\ntext=" + text + "\ndate=" + date + "]";
  }
}
