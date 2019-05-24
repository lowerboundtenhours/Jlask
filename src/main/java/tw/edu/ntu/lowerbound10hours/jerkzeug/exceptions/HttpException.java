package tw.edu.ntu.lowerbound10hours.jerkzeug.exceptions;

// import tw.edu.ntu.lowerbound10hours.jerkzeug.wrappers.Response;

/* HTTP excetptions.
 *
 * Baseclass for all HTTP exceptions.  This exception can be called as WSGI
 * application to render a default error page or you can catch the subclasses
 * of it independently and render nicer error messages. */
@SuppressWarnings("serial")
public class HttpException extends Exception {
  protected Integer code;
  protected String description;
  // protected Response response;

  // TODO:
  // public HttpException(String description, Response response) {}
  public HttpException() {
    super();
  }

  /**
   * Main constructer.
   * @param description error message
   */
  public HttpException(String description) {
    super(description);
    if (description != null) {
      this.description = description;
    }
    // this.response = response;
  }

  public Integer getCode() {
    return this.code;
  }

  public String getDescription() {
    return this.description;
  }

  @Override
  public String toString() {
    return super.toString();
  }

  @Override
  public String getMessage() {
    return String.format("[status %d] %s", this.code, this.description);
  }
}
