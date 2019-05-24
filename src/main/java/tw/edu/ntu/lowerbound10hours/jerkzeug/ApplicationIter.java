package tw.edu.ntu.lowerbound10hours.jerkzeug;

import java.util.Iterator;
import java.util.List;

public class ApplicationIter<T> implements Iterable<T> {

  private List<T> list;

  public ApplicationIter() {}

  public ApplicationIter(List<T> list) {
    this.list = list;
  }

  @Override
  public Iterator<T> iterator() {
    return list.iterator();
  }
}
