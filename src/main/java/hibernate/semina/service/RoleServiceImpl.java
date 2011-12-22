package hibernate.semina.service;

import hibernate.semina.dao.RoleDao;
import hibernate.semina.generic.AbstractGenericService;
import hibernate.semina.generic.GenericDao;
import hibernate.semina.model.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl extends AbstractGenericService<Role, Long> implements RoleService {

	@Autowired
	private RoleDao menuDao;

	@Override
	protected GenericDao<Role, Long> getGenericDao() {
		return menuDao;
	}

}
