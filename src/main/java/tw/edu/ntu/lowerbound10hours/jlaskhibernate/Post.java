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

  /** The blog post table. */
  public Post(String title, String body, int authorId, String username) {
    this.title = title;
    this.body = body;
    this.authorId = authorId;
    this.username = username;
    this.created = new Date();
  }

  /** Default constructor. */
  public Post() {}

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "title")
  private String title;

  @Column(name = "body")
  private String body;

  @Column(name = "authorId")
  private int authorId;

  @Column(name = "username")
  private String username;

  @Column(name = "created")
  private Date created;

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

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public int getAuthorId() {
    return authorId;
  }

  public void setAuthorId(int authorId) {
    this.authorId = authorId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  @Override
  public String toString() {
    return "Post [id="
        + id
        + "\ntitle="
        + title
        + "\nbody="
        + body
        + "\nauthorId"
        + authorId
        + "\nusername"
        + username
        + "\ncreated="
        + created
        + "]";
  }
}
