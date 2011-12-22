package hibernate.semina.auth;

import hibernate.semina.model.User;
import hibernate.semina.model.UserGroup;

import java.util.Map;

public class Authentication {

	public static final String SUPER_USER_ID = "admin";

	private User user;
	private UserGroup group;
	private Map<Long, Authority> authorityMap;

	public Authentication(User user, UserGroup group, Map<Long, Authority> authorityMap) {
		super();
		this.user = user;
		this.group = group;
		this.authorityMap = authorityMap;
	}

	public Authority getAuthority(Long roleId) {
		return authorityMap.get(roleId);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserGroup getGroup() {
		return group;
	}

	public void setUserGroup(UserGroup group) {
		this.group = group;
	}

	public Map<Long, Authority> getAuthorityMap() {
		return authorityMap;
	}

	public void setAuthorityMap(Map<Long, Authority> authorities) {
		this.authorityMap = authorities;
	}


}
