package hibernate.semina.generic;

import java.util.List;

public interface GenericService<M extends Model<ID>, ID> {
	public long count();

	public long count(M model);

	public M create(M model);

	public int delete(M model);

	public int delete(ID id);

	public M findById(ID id);

	public List<M> find();

	public List<M> find(Sort sort);

	public List<M> find(Pageable pageable);

	public List<M> find(M model);

	public List<M> find(M model, Pageable pageable);

	public List<M> find(M model, Sort sort);

	public M findOne();

	public M findOne(Sort sort);

	public M findOne(M model);

	public M findOne(M model, Sort sort);

	public int update(M model);

	public int save(M model);
}
