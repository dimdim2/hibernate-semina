package hibernate.semina.auth.exception;

public class InvalidUserException extends AuthenticationException {
	public InvalidUserException(String msg) {
		super(msg);
	}

	public InvalidUserException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
