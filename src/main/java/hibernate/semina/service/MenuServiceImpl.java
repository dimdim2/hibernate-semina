package hibernate.semina.service;

import hibernate.semina.dao.MenuDao;
import hibernate.semina.generic.AbstractGenericService;
import hibernate.semina.generic.GenericDao;
import hibernate.semina.model.Menu;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MenuServiceImpl extends AbstractGenericService<Menu, Long> implements MenuService {

	@Autowired
	private MenuDao menuDao;

	@Override
	protected GenericDao<Menu, Long> getGenericDao() {
		return menuDao;
	}

	@Override
	public Menu create(Menu menu) {
		int order = (int)(menuDao.countChildMenu(menu.getParentId()) + 1);
		menu.setOrder(order);
		return menuDao.create(menu);
	}

	@Override
	public List<Menu> findChildMenus(Long id) {
		return menuDao.findChildMenus(id);
	}

	@Override
	public int delete(Menu menu) {
		int removeIndex = menu.getOrder();
		List<Menu> siblingMenus = menuDao.findChildMenus(menu.getParentId());
		for (Menu siblingMenu : siblingMenus) {
			int siblingOrder = siblingMenu.getOrder();
			if (siblingOrder >= removeIndex) {
				siblingMenu.setOrder(--siblingOrder);
				update(siblingMenu);
			}
		}

		return super.delete(menu);
	}

	@Override
	public void moveMenu(long id, long oldParentId, long newParentId, int index) {
		Menu menu = findById(id);

		int originOrder = menu.getOrder();
		if (oldParentId == newParentId) {
			List<Menu> newSiblingMenus = menuDao.findChildMenus(newParentId);
			for (Menu newSiblingMenu : newSiblingMenus) {
				int siblingOrder = newSiblingMenu.getOrder();
				if (originOrder > siblingOrder) {
					if (siblingOrder >= index && siblingOrder < originOrder) {
						newSiblingMenu.setOrder(++siblingOrder);
						update(newSiblingMenu);
					}

				} else {
					if (siblingOrder <= index && siblingOrder > originOrder) {
						newSiblingMenu.setOrder(--siblingOrder);
						update(newSiblingMenu);
					}
				}
			}

		} else {
			List<Menu> newSiblingMenus = menuDao.findChildMenus(newParentId);
			for (Menu newSiblingMenu : newSiblingMenus) {
				int siblingOrder = newSiblingMenu.getOrder();
				if (siblingOrder >= index) {
					newSiblingMenu.setOrder(++siblingOrder);
					update(newSiblingMenu);
				}
			}

			List<Menu> oldSiblingMenus = menuDao.findChildMenus(oldParentId);
			for (Menu oldSiblingMenu : oldSiblingMenus) {
				int siblingOrder = oldSiblingMenu.getOrder();
				if (siblingOrder >= originOrder) {
					oldSiblingMenu.setOrder(--siblingOrder);
					update(oldSiblingMenu);
				}
			}
		}

		menu.setParentId(newParentId);
		menu.setOrder(index);
		menu.setUpdateTime(new Date());
		update(menu);
	}

}
