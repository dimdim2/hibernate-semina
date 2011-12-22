package hibernate.semina.generic;

import hibernate.semina.generic.Sort.Direction;

public class Pageable {

	private final int page;
	private final int rowPerPage;
	private final Sort sort;

	public Pageable(int page, int rowPerPage) {
		this(page, rowPerPage, null);
	}

	public Pageable(int page, int rowPerPage, Direction direction, String... properties) {
		this(page, rowPerPage, new Sort(direction, properties));
	}

	public Pageable(int page, int rowPerPage, Sort sort) {
		if (page <= 0) {
			page = 1;
		}

		if (rowPerPage <= 0) {
			rowPerPage = Integer.MAX_VALUE;
		}

		this.page = page;
		this.rowPerPage = rowPerPage;
		this.sort = sort;
	}

	public int getRowPerPage() {
		return rowPerPage;
	}

	public int getPage() {
		return page;
	}

	public int getOffset() {
		return (page - 1) * rowPerPage;
	}

	public Sort getSort() {
		return sort;
	}

}
