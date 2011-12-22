package hibernate.semina.dao;

import hibernate.semina.generic.GenericDao;
import hibernate.semina.model.Function;

public interface FunctionDao extends GenericDao<Function, Long> {

	Function getFindByUrl(String url);

}
