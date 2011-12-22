package hibernate.semina.auth;

import hibernate.semina.auth.exception.AuthenticationException;


public interface AuthenticationManager {
	public Authentication authenticate(String userId, String password) throws AuthenticationException;
}
