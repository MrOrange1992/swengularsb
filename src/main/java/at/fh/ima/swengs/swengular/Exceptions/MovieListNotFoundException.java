package at.fh.ima.swengs.swengular.Exceptions;

public class MovieListNotFoundException extends RuntimeException
{
    public MovieListNotFoundException() { }

    public MovieListNotFoundException(String message) { super(message); }

    public MovieListNotFoundException(String message, Throwable cause) { super(message, cause); }

    public MovieListNotFoundException(Throwable cause) { super(cause); }

    public MovieListNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
