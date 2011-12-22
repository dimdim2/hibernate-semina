package hibernate.semina.dao;

import hibernate.semina.generic.HibernateGenericDao;
import hibernate.semina.model.UserGroup;

import org.springframework.stereotype.Repository;

@Repository
public class UserGroupDaoImpl extends HibernateGenericDao<UserGroup, Long> implements UserGroupDao {

}
