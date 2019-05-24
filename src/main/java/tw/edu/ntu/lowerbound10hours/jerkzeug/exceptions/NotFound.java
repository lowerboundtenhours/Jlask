package tw.edu.ntu.lowerbound10hours.jerkzeug.exceptions;

@SuppressWarnings("serial")
public class NotFound extends HttpException { 
  public NotFound() {
    super();
    super.code = 404;
    super.description = "The requested URL was not found on the server. If you entered"
    + " the URL manually please check your spelling and try again.";

  }
}

