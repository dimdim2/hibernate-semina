package hibernate.semina.dao;

import hibernate.semina.generic.HibernateGenericDao;
import hibernate.semina.model.User;

import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends HibernateGenericDao<User, String> implements UserDao {

}
