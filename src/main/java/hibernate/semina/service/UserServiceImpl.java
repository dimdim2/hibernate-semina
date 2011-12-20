package hibernate.semina.service;

import hibernate.semina.dao.UserDao;
import hibernate.semina.generic.AbstractGenericService;
import hibernate.semina.generic.GenericDao;
import hibernate.semina.model.User;

import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl extends AbstractGenericService<User, String> implements UserService {

	@Autowired
	private UserDao menuDao;

	@Override
	protected GenericDao<User, String> getGenericDao() {
		return menuDao;
	}

}
