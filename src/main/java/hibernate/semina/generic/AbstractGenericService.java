package hibernate.semina.generic;

import java.util.List;

public abstract class AbstractGenericService<M extends Model<ID>, ID> implements GenericService<M, ID> {

	protected abstract GenericDao<M, ID> getGenericDao();

	@Override
	public long count() {
		return getGenericDao().count();
	}

	@Override
	public long count(M model) {
		return getGenericDao().count(model);
	}

	@Override
	public M create(M model) {
		return getGenericDao().create(model);
	}

	@Override
	public int delete(M model) {
		return getGenericDao().delete(model);
	}

	@Override
	public int delete(ID id) {
		return getGenericDao().delete(id);
	}

	@Override
	public M findById(ID id) {
		return getGenericDao().findById(id);
	}

	@Override
	public List<M> find() {
		return getGenericDao().find();
	}

	@Override
	public List<M> find(Sort sort) {
		return getGenericDao().find(sort);
	}

	@Override
	public List<M> find(Pageable pageable) {
		return getGenericDao().find(pageable);
	}

	@Override
	public List<M> find(M model) {
		return getGenericDao().find(model);
	}

	@Override
	public List<M> find(M model, Pageable pageable) {
		return getGenericDao().find(model, pageable);
	}

	@Override
	public List<M> find(M model, Sort sort) {
		return getGenericDao().find(model, sort);
	}

	@Override
	public M findOne() {
		return getGenericDao().findOne();
	}

	@Override
	public M findOne(Sort sort) {
		return getGenericDao().findOne(sort);
	}

	@Override
	public M findOne(M model) {
		return getGenericDao().findOne(model);
	}

	@Override
	public M findOne(M model, Sort sort) {
		return getGenericDao().findOne(model, sort);
	}

	@Override
	public int update(M model) {
		return getGenericDao().update(model);
	}

	@Override
	public int save(M model) {
		return getGenericDao().save(model);
	}

}