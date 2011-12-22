package hibernate.semina.service;

import hibernate.semina.generic.GenericService;
import hibernate.semina.model.Function;

public interface FunctionService extends GenericService<Function, Long> {

	Function findByUrl(String url);

}
