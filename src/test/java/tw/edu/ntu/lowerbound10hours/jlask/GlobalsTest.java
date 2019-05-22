package tw.edu.ntu.lowerbound10hours.jlask;

import static org.testng.Assert.assertEquals;

import java.util.Stack;
import org.testng.annotations.Test;

public class GlobalsTest {
  @Test
  public void testGetRequestContextStack() {
    Stack<RequestContext> stack1 = Globals.getRequestContextStack();
    stack1.push(new RequestContext());
    Stack<RequestContext> stack2 = Globals.getRequestContextStack();
    assertEquals(stack2.size(), stack1.size());
    assertEquals(stack2.peek(), stack1.peek());
  }
}
