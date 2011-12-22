package hibernate.semina.dao;

import hibernate.semina.generic.HibernateGenericDao;
import hibernate.semina.model.Menu;

import org.springframework.stereotype.Repository;

@Repository
public class MenuDaoImpl extends HibernateGenericDao<Menu, Long> implements MenuDao {

}
