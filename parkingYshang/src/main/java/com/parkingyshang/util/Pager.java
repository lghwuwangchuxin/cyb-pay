package com.parkingyshang.util;
public class Pager  implements java.io.Serializable{
	private static final long serialVersionUID = 1544686141542626760L;
	private int curPage = 0;		//当前第几页
	private int pageSize = 3;		//每页显示数量
	private int fromPage;			//从第几条开始查
	private boolean nextPage;		//是否显示下一页
	private boolean prePage;        //是否显示上一页
	public boolean isPrePage() {
		return prePage;
	}
	public void setPrePage(boolean prePage) {
		this.prePage = prePage;
	}
	public boolean isNextPage() {
		return nextPage;
	}
	public void setNextPage(boolean nextPage) {
		this.nextPage = nextPage;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getFromPage() {
		return fromPage;
	}
	public void setFromPage(int fromPage) {
		this.fromPage = fromPage;
	}
	
	 
}
