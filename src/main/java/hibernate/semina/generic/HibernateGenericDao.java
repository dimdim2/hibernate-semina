package hibernate.semina.generic;

import hibernate.semina.generic.Sort.Direction;
import hibernate.semina.generic.Sort.Order;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;

public class HibernateGenericDao<M extends Model<ID>, ID> implements GenericDao<M, ID> {

	private Class<M> persistentClass;

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public HibernateGenericDao() {
		this.persistentClass = (Class<M>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Class<M> getPersistentClass() {
		return persistentClass;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public M create(M model) {
		getCurrentSession().save(model);
		return model;
	}

	@Override
	public int delete(ID id) {
		M user = findById(id);
		if (user != null) {
			return delete(user);
		} else {
			return 0;
		}
	}

	@Override
	public int delete(M model) {
		getCurrentSession().delete(model);
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public M findById(ID id) {
		return (M)getCurrentSession().get(persistentClass, (Serializable)id);
	}

	public Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}

	@Override
	public int count() {
		return count(null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<M> find() {
		return getCurrentSession().createCriteria(persistentClass).list();
	}

	@Override
	public List<M> find(Pageable pageable) {
		return find(null, pageable);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<M> find(M model) {
		return getCriteria(model).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<M> find(M model, Pageable pageable) {
		Criteria criteria = getCriteria(model);

		if (pageable != null) {
			criteria.setFirstResult(pageable.getOffset());
			criteria.setMaxResults(pageable.getRowPerPage());

			addOrderToCriterion(criteria, pageable.getSort());
		}

		return criteria.list();
	}

	@Override
	public List<M> find(Sort sort) {
		return find(null, sort);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<M> find(M model, Sort sort) {
		Criteria criteria = null;
		if (model != null) {
			criteria = getCriteria(model);
		} else {
			criteria = getCurrentSession().createCriteria(persistentClass);
		}

		addOrderToCriterion(criteria, sort);

		return criteria.list();
	}

	@Override
	public M findOne() {
		List<M> result = find(new Pageable(1, 1));

		if (result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}

	@Override
	public M findOne(Sort sort) {
		List<M> result = find(new Pageable(1, 1, sort));

		if (result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}

	@Override
	public M findOne(M model) {
		List<M> result = find(model, new Pageable(1, 1));

		if (result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}

	@Override
	public M findOne(M model, Sort sort) {
		List<M> result = find(model, new Pageable(1, 1, sort));

		if (result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}

	@Override
	public int count(M model) {
		Criteria criteria = getCriteria(model);
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}

	protected Criteria getCriteria(M model) {
		Criteria criteria = getCurrentSession().createCriteria(model.getClass());

		if (model != null) {
			Example example = Example.create(model)
					.enableLike(MatchMode.START);
			criteria.add(example);
		}

		return criteria;
	}

	@Override
	public int update(M model) {
		getCurrentSession().update(model);
		return 1;
	}

	@Override
	public int save(M model) {
		getCurrentSession().saveOrUpdate(model);
		return 1;
	}

	private void addOrderToCriterion(Criteria criteria, Sort sort) {
		if (sort != null) {
			for (Order order : sort) {
				if (order.getDirection().equals(Direction.ASC)) {
					criteria.addOrder(org.hibernate.criterion.Order.asc(order.getProperty()));
				} else {
					criteria.addOrder(org.hibernate.criterion.Order.desc(order.getProperty()));
				}
			}
		}
	}
}
