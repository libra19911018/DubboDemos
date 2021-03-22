package libra.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询结果封装类
 * @author LiuBC
 * @param <T>
 */
public class Page<T> implements Serializable {

	private static final long serialVersionUID = 2497216152880521982L;
	private List<T> rows;   // 结果List
	private int pageNumber; // 当前页数
	private int pageSize;   // 每页行数
	private int totalPage;  // 总页数
	private int total;   // 总行数

	public Page(List<T> list, int pageNumber, int pageSize, int totalPage,
			int totalRow) {
		this.rows = list;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalPage = totalPage;
		this.total = totalRow;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
