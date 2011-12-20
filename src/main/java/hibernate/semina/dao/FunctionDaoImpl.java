package hibernate.semina.dao;

import hibernate.semina.generic.HibernateGenericDao;
import hibernate.semina.model.Function;

import org.springframework.stereotype.Repository;

@Repository
public class FunctionDaoImpl extends HibernateGenericDao<Function, Long> implements FunctionDao {

}
