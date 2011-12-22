package hibernate.semina.dao;

import hibernate.semina.generic.GenericDao;
import hibernate.semina.model.Menu;

import java.util.List;

public interface MenuDao extends GenericDao<Menu, Long> {

	List<Menu> findChildMenus(Long id);

	long countChildMenu(Long parentId);

}
