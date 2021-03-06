package hibernate.semina.service;

import hibernate.semina.dao.UserDao;
import hibernate.semina.generic.AbstractGenericService;
import hibernate.semina.generic.GenericDao;
import hibernate.semina.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl extends AbstractGenericService<User, String> implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	protected GenericDao<User, String> getGenericDao() {
		return userDao;
	}

}
