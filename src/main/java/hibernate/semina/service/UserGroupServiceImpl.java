package hibernate.semina.service;

import hibernate.semina.dao.UserGroupDao;
import hibernate.semina.generic.AbstractGenericService;
import hibernate.semina.generic.GenericDao;
import hibernate.semina.model.UserGroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserGroupServiceImpl extends AbstractGenericService<UserGroup, Long> implements UserGroupService {

	@Autowired
	private UserGroupDao menuDao;

	@Override
	protected GenericDao<UserGroup, Long> getGenericDao() {
		return menuDao;
	}

}
