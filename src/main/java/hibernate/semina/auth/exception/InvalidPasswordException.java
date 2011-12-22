package hibernate.semina.auth.exception;

public class InvalidPasswordException extends AuthenticationException {
	public InvalidPasswordException(String msg) {
		super(msg);
	}

	public InvalidPasswordException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
