package cn.tedu.ttms.common.web;

import java.io.Serializable;

/**封装具体的分页信息*/
public class PageObject implements Serializable{

	private static final long serialVersionUID = -8753809986545361268L;
	/**当前页*/
	private int pageCurrent=1;
	/**每页最多能显示的记录数*/
	private int pageSize=2;
	/**总记录数*/
	private int rowCount;
	/**总页数*/
	private int pageCount;
	/**当前页的起始计数,即上一页的最后一条记录位置
	 * 对应limit startIndex,pageSize;
	 * */
	private int startIndex;
	
	public int getPageCurrent() {
		return pageCurrent;
	}
	public void setPageCurrent(int pageCurrent) {
		this.pageCurrent = pageCurrent;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	
}
