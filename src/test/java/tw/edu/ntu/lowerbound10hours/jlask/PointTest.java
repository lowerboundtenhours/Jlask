package tw.edu.ntu.lowerbound10hours.java;

import static org.testng.Assert.*;

public class PointTest {

  @org.testng.annotations.Test
  public void testGetX() throws Exception {
    Point p = new Point(3, 4);
    assertEquals(p.getX(), 3);
  }

  @org.testng.annotations.Test
  public void testSetX() throws Exception {
    Point p = new Point(3, 4);
    p.setX(5);
    assertEquals(p.getX(), 5);
  }

  @org.testng.annotations.Test
  public void testGetY() throws Exception {
    Point p = new Point(3, 4);
    assertEquals(p.getY(), 4);
  }

  @org.testng.annotations.Test
  public void testSetY() throws Exception {
    Point p = new Point(3, 4);
    p.setY(6);
    assertEquals(p.getY(), 6);
  }

  @org.testng.annotations.Test
  public void testToString() throws Exception {
    Point p = new Point(3, 4);
    assertEquals(p.toString(), "(3, 4)");
  }
}
