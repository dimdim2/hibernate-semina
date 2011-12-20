package hibernate.semina.service;

import hibernate.semina.dao.MenuDao;
import hibernate.semina.generic.AbstractGenericService;
import hibernate.semina.generic.GenericDao;
import hibernate.semina.model.Menu;

import org.springframework.beans.factory.annotation.Autowired;

public class MenuServiceImpl extends AbstractGenericService<Menu, Long> implements MenuService {

	@Autowired
	private MenuDao menuDao;

	@Override
	protected GenericDao<Menu, Long> getGenericDao() {
		return menuDao;
	}

}
