package hibernate.semina.service;

import hibernate.semina.generic.GenericService;
import hibernate.semina.model.Menu;

import java.util.List;

public interface MenuService extends GenericService<Menu, Long> {

	List<Menu> findChildMenus(Long id);

	void moveMenu(long id, long oldParentId, long newParentId, int index);

}
