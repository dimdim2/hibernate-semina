package hibernate.semina.auth;

import hibernate.semina.auth.exception.AuthenticationException;
import hibernate.semina.auth.exception.InvalidPasswordException;
import hibernate.semina.auth.exception.InvalidUserException;
import hibernate.semina.dao.UserDao;
import hibernate.semina.model.GroupAuth;
import hibernate.semina.model.User;
import hibernate.semina.model.UserGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthenticationManagerImpl implements AuthenticationManager {

	@Autowired
	private UserDao userDao;

	public Authentication authenticate(String userId, String password) throws AuthenticationException {
		User user = userDao.findById(userId);

		if (user == null) {
			throw new InvalidUserException(String.format("Not Exist User [UserId:%s]", userId));
		}

		if (!user.getPassword().equals(password)) {
			throw new InvalidPasswordException(String.format("Invalid passowrd [UserId:%s]", userId));
		}

		UserGroup group = user.getGroup();

		Map<Long, Authority> authorityMap = new HashMap<Long, Authority>();
		List<GroupAuth> authorities = group.getAuthorities();
		for (GroupAuth authority : authorities) {
			setAuthorityMap(authorityMap, authority.getRole().getId(), authority.getAuthority());
		}

		return new Authentication(user, group, authorityMap);
	}

	private void setAuthorityMap(Map<Long, Authority> authorityMap, Long roleId, String authLevel) {
		Authority authority = authorityMap.get(roleId);
		if (authority != null) {
			authority.setAuthLevel(authLevel);
			authorityMap.put(roleId, authority);
		} else {
			authorityMap.put(roleId, new Authority(authLevel));
		}
	}
}
