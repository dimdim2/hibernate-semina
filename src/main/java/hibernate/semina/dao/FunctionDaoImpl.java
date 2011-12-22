package hibernate.semina.dao;

import hibernate.semina.generic.HibernateGenericDao;
import hibernate.semina.model.Function;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class FunctionDaoImpl extends HibernateGenericDao<Function, Long> implements FunctionDao {

	@Override
	public Function getFindByUrl(String url) {
		return (Function)getCurrentSession().createCriteria(Function.class)
				.add(Restrictions.eq("url", url))
				.setMaxResults(1)
				.uniqueResult();
	}

}
