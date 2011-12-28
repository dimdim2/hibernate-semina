package hibernate.semina.service;

import hibernate.semina.dao.FunctionDao;
import hibernate.semina.generic.AbstractGenericService;
import hibernate.semina.generic.GenericDao;
import hibernate.semina.model.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FunctionServiceImpl extends AbstractGenericService<Function, Long> implements FunctionService {

	@Autowired
	private FunctionDao menuDao;

	@Override
	protected GenericDao<Function, Long> getGenericDao() {
		return menuDao;
	}

	@Override
	public Function findByUrl(String url) {
		return menuDao.findByUrl(url);
	}

}
