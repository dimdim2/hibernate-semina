package hibernate.semina.common;

import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

public class PaginatedListImpl implements PaginatedList {
	public static final int OBJECTS_PER_PAGE = 10; // default

	private List<?> list;
	private int objectsPerPage = OBJECTS_PER_PAGE;
	private int pageNumber;
	private String sortCriterion;
	private SortOrderEnum sortDirection;
	private int fullListSize;

	public PaginatedListImpl() {}

	public PaginatedListImpl(List<?> list, int pageNumber, int fullListSize) {
		this.list = list;
		this.fullListSize = fullListSize;
		this.setPageNumber(pageNumber);
	}

	public PaginatedListImpl(List<?> list, int pageNumber, int fullListSize, int objectsPerPage) {
		this.list = list;
		this.fullListSize = fullListSize;
		this.setPageNumber(pageNumber);
		this.objectsPerPage = objectsPerPage;
	}

	public int getStartIndex() {
		return (this.pageNumber - 1) * objectsPerPage + 1;
	}

	@Override
	public int getFullListSize() {
		return fullListSize;
	}

	@Override
	public List<?> getList() {
		return this.list;
	}

	@Override
	public int getObjectsPerPage() {
		return this.objectsPerPage;
	}

	public void setObjectsPerPage(int objectsPerPage) {
		this.objectsPerPage = objectsPerPage;
	}

	@Override
	public int getPageNumber() {
		return this.pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		if (pageNumber <= 0) {
			pageNumber = 1;
		}
		this.pageNumber = pageNumber;
	}

	@Override
	public String getSearchId() {
		return null;
	}

	@Override
	public String getSortCriterion() {
		return this.sortCriterion;
	}

	public void setSortCriterion(String sortCriterion) {
		this.sortCriterion = sortCriterion;
	}

	@Override
	public SortOrderEnum getSortDirection() {
		return this.sortDirection;
	}

	public void setSortDirection(SortOrderEnum sortDirection) {
		this.sortDirection = sortDirection;
	}

	public int getFullPageSize() {
		int pageSize = fullListSize / objectsPerPage;
		if ((fullListSize % objectsPerPage) > 0) {
			pageSize++;
		}
		return pageSize;
	}
}
