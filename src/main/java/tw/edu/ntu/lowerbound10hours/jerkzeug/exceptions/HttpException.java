package tw.edu.ntu.lowerbound10hours.jerkzeug.exceptions;

// import tw.edu.ntu.lowerbound10hours.jerkzeug.wrappers.Response;

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

  public HttpException(String description) {
    super(description);
    if (description != null)
      this.description = description;
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
    return String.format("[status %d] %s", this.code,  this.description);
  }
}

