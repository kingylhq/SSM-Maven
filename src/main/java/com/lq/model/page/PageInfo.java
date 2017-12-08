package com.lq.model.page;

import java.io.Serializable;
import java.util.List;

/**
 * Page对象
 * @author lq
 * 2017-12-6 10:09:56
 * @param <T>
 */
public class PageInfo<T> implements Serializable {

	/**
	 * serialVersionUID:序列化ID，缓存需要.
	 *
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = -117781956848297165L;
	/**
	 * 默认分页条数
	 */
	public static final int DEFAULT_PAGE_SIZE = 20;
	
	public static final String NO_NEED_PAGE = "noNeedPage";
	public static final String PAGE = "page";

	/**
	 * 页面大小
	 */
	private int pageSize;
	/**
	 * 当前页
	 */
	private int currentPage;
	/**
	 * 上一页
	 */
	private int prePage;
	/**
	 * 下一页
	 */
	private int nextPage;
	/**
	 * 分页总数
	 */
	private int totalPage;
	/**
	 * 结果总数
	 */
	private int totalCount;
	/**
	 * 结果总数供EasyUI-datagrid使用
	 */
	@SuppressWarnings("unused")
	private int total;

	/* sort：排序的列的字段名 */
	private String sort;

	/* order：排序的列的顺序 */
	private String order;

	/**
	 * 数据库查询出来的结果集
	 */
	private List<T> result;
	/**
	 * 数据库查询出来的结果集供EasyUI-datagrid使用
	 */
	private List<T> rows;

	/**
	 * 页脚
	 */
	private List<?> footer;

	private boolean paging = true;

	public PageInfo() {
		this(1, DEFAULT_PAGE_SIZE);
	}

	public PageInfo(int currentPage, int pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;

	}

	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * footer.
	 *
	 * @return the footer
	 * @since JDK 1.7
	 */
	public List<?> getFooter() {
		return footer;
	}

	public int getNextPage() {
		return nextPage;
	}

	public String getOrder() {
		return order;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPrePage() {
		return prePage;
	}

	public List<?> getResult() {
		return result;
	}

	public List<?> getRows() {
		return rows;
	}

	public String getSort() {
		return sort;
	}

	public int getTotal() {
		return totalCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * footer.
	 *
	 * @param footer
	 *            the footer to set
	 * @since JDK 1.7
	 */
	public void setFooter(List<?> footer) {
		this.footer = footer;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setTotal(int total) {
		this.total = total;
		this.totalCount = total;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public boolean isPaging() {
		return paging;
	}

	public void setPaging(boolean paging) {
		this.paging = paging;
	}

}
