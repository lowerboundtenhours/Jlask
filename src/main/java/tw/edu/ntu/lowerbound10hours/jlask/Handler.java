package tw.edu.ntu.lowerbound10hours.jlask;

import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Response;

public abstract class Handler {
  public Handler() {}
  public abstract String call(Exception e);
}
