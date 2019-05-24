package tw.edu.ntu.lowerbound10hours.jerkzeug.exceptions;

@SuppressWarnings("serial")
public class InternalServerError extends HttpException { 
  public InternalServerError() {
    super();
    super.code = 500;
    super.description = "The server encountered an internal error and was unable to"
      + " complete your request. Either the server is overloaded or"
      + " there is an error in the application.";
  }
}
