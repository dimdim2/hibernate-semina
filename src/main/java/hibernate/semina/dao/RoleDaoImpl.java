package hibernate.semina.dao;

import hibernate.semina.generic.HibernateGenericDao;
import hibernate.semina.model.Role;

import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends HibernateGenericDao<Role, Long> implements RoleDao {

}
