package hibernate.semina.dao;

import hibernate.semina.generic.HibernateGenericDao;
import hibernate.semina.model.Menu;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MenuDaoImpl extends HibernateGenericDao<Menu, Long> implements MenuDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> findChildMenus(Long id) {
		return getCurrentSession().createCriteria(Menu.class)
				.add(Restrictions.eq("parentId", id))
				.addOrder(Order.asc("order"))
				.list();
	}

	@Override
	public long countChildMenu(Long parentId) {
		return (Long)getCurrentSession().createCriteria(Menu.class)
				.add(Restrictions.eq("parentId", parentId))
				.setProjection(Projections.count("id"))
				.uniqueResult();
	}

	@Override
	public int delete(Menu menu) {
		getCurrentSession().createQuery("delete from Menu where parentId = :menuId")
				.setLong("menuId", menu.getId())
				.executeUpdate();

		return super.delete(menu);
	}

}
