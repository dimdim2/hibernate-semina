package hibernate.semina.auth.exception;

public class AuthenticationException extends Exception {
	public AuthenticationException(String msg) {
		super(msg);
	}

	public AuthenticationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
