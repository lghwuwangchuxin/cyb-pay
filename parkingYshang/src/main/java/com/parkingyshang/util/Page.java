package com.parkingyshang.util;

import java.util.Map;

public class Page {

	public static final int DEFAULT_SIZEPERPAGE = 10; //默认的每页显示条数为10
	
	private int totalPage;  //分页时总页数
	
	private int sizePerPage; //每页显示条数
	
	private int allRowCount; //总记录的各数
	
	private int curPage; //当前页
	
	private int leftPageCount; //分页导航当前页左边的页数个数
	
	private int rightPageCount; //分页导航当前右边的页数个数
	
	private String[] leftSideBarNumber;
	
	private String[] rightSideBarNumber;
	
	private Integer fromOffset; // 当前分页起始位置 = 当前页* sizePerPage
	
	
	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getSizePerPage() {
		return sizePerPage;
	}

	public void setSizePerPage(int sizePerPage) {
		this.sizePerPage = sizePerPage;
	}

	public int getAllRowCount() {
		return allRowCount;
	}

	public void setAllRowCount(int allRowCount) {
		this.allRowCount = allRowCount;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getLeftPageCount() {
		return leftPageCount;
	}

	public void setLeftPageCount(int leftPageCount) {
		this.leftPageCount = leftPageCount;
	}

	public int getRightPageCount() {
		return rightPageCount;
	}

	public void setRightPageCount(int rightPageCount) {
		this.rightPageCount = rightPageCount;
	}

	
	public Integer getFromOffset() {
		return fromOffset;
	}

	
	public void setFromOffset(Integer fromOffset) {
		this.fromOffset = fromOffset;
	}

	public Page(){
		
	}
	/**
	 * 
	 * @param curPage       当前页
	 * @param sizePerPage   每页记录数
	 * @param allRowCount   总记录数
	 */
	public Page (int curPage, int sizePerPage , int allRowCount ){
		if(curPage < 0){
			throw new IllegalArgumentException();
		}
		
		if(sizePerPage <= 0){
			throw new IllegalArgumentException();
		}
		
		if(allRowCount <=0){
			throw new IllegalArgumentException("记录总数(allRowCount)必须大于0的整数");
		}
		
		this.allRowCount = allRowCount;
		this.sizePerPage = sizePerPage;
		this.curPage = curPage;
		//计算总页数
		if(allRowCount % sizePerPage != 0){
			totalPage = allRowCount / sizePerPage + 1 ;
		}else{
			totalPage = allRowCount / sizePerPage ;
		}
		
		if(curPage > totalPage){
			throw new IllegalArgumentException();
		}
		//当前分页启始位置
		this.fromOffset = (this.curPage - 1) * sizePerPage;
		//计算分页显示样式
		if (curPage > 1) {
			if (curPage > 5) {
				this.leftPageCount =  5;
			} else if(curPage == 5){
				this.leftPageCount = 4;
			}else {
				this.leftPageCount = curPage;
			}

		}
		if (curPage < totalPage) {
			if (totalPage - curPage > 5) {
				this.rightPageCount = 5;
			}else {
				this.rightPageCount = totalPage - curPage;
			}
		}
	}
	
	
	public Page (int curPage , int allRowCount ){
		this.sizePerPage = DEFAULT_SIZEPERPAGE;
		if(curPage < 0){
			throw new IllegalArgumentException();
		}
		
		if(sizePerPage <= 0){
			throw new IllegalArgumentException();
		}
		
		if(allRowCount < 0){
			throw new IllegalArgumentException();
		}
		
		this.allRowCount = allRowCount;
		this.curPage = curPage;
		if(allRowCount % sizePerPage != 0){
			totalPage = allRowCount / sizePerPage + 1 ;
		}else{
			totalPage = allRowCount / sizePerPage ;
		}
		
		if(curPage > totalPage){
			throw new IllegalArgumentException();
		}
		
		this.fromOffset = (this.curPage - 1) * sizePerPage;
		
		if (curPage > 1) {
			if (curPage > 5) {
				this.leftPageCount =  5;
			} else {
				this.leftPageCount = curPage;
			}

		}
		if (curPage < totalPage) {
			if (totalPage - curPage > 5) {
				this.rightPageCount = 5;
			}

			else {
				this.rightPageCount = totalPage - curPage;
			}
		}
	}
	
	// 分页导航变长
	public void style1(){
		int sides_size = 9;
		if(curPage <=0)
			throw new IllegalArgumentException();
		if(totalPage == 0)
			return ;
		//会变化
		if (curPage > 1) {
			if (curPage > sides_size) {
				this.leftPageCount = sides_size;
			} else {
				this.leftPageCount = curPage - 1;
			}
		
		}
		
		if (totalPage - curPage > sides_size) {
			this.rightPageCount = sides_size;
		}
		else {
			this.rightPageCount = totalPage - curPage;
		}		
		detailRihtSideAndLeftSide();
	}
	
	public void style1(int sides_size){		
		if(curPage <=0)
			throw new IllegalArgumentException();
		if(totalPage == 0)
			return ;
		//会变化
		if (curPage > 1) {
			if (curPage > sides_size) {
				this.leftPageCount = sides_size;
			} else {
				this.leftPageCount = curPage - 1;
			}

		}
		
		if (totalPage - curPage > sides_size) {
			this.rightPageCount = sides_size;
		}
		else {
			this.rightPageCount = totalPage - curPage;
		}
		detailRihtSideAndLeftSide();
		
	}
	
	private void detailRihtSideAndLeftSide(){
		if(this.leftPageCount > 0){
			leftSideBarNumber = new String[this.leftPageCount];	
			for(int i= this.leftPageCount ,j=0; j<this.leftPageCount;i--,j++){
				leftSideBarNumber[j] = String.valueOf(this.curPage - i);
			}
		}	
		if(this.rightPageCount > 0){
				rightSideBarNumber = new String[this.rightPageCount];	
				for(int i= 1 ,j=0; j<this.rightPageCount;i++,j++){
					rightSideBarNumber[j] = String.valueOf(this.curPage + i);
			}
		}
	}
	
	//分页导航定长
	public void style2(){
		
		if(totalPage == 0)
			return ;
		int Max = 11; //当前页左边 + 当前页 + 当前页右边 的总显示数
		int side_size = Max / 2;
		if(totalPage < Max){
			Max = totalPage;
		}
		if(curPage <= 0){
			throw new IllegalArgumentException();
		}
		else if(curPage > side_size && totalPage  - curPage >= side_size  ){ 
			leftPageCount = side_size;
			rightPageCount = side_size;
		}else{
			if(curPage < side_size){ // 如果左边小于5
				leftPageCount = curPage -1 ;
				//当前当前页右边页数 小于Max - 左边的页数的个数 那么右边页取totalPage  - curPage
				if(totalPage  - curPage < Max - leftPageCount ){
					rightPageCount = totalPage  - curPage;
				}
				else{
					rightPageCount = Max- leftPageCount - 1 ;
				}
			}
			
			//如果右边部分小于5的哈
			if(totalPage  - curPage < side_size){
				rightPageCount = totalPage  - curPage;
				if(curPage < Max - rightPageCount){
					leftPageCount = curPage - 1;
				}else{
					leftPageCount = Max - rightPageCount -1;
				}
			}
		}
		
		detailRihtSideAndLeftSide();
		/*System.out.println("当前页 " + curPage + "页");
		System.out.println(leftPageCount);
		System.out.println(rightPageCount);*/
	}
		
	
	public void style2(int Max){
		if(totalPage == 0)
			return ;
		//int Max = 11; //当前页左边 + 当前页 + 当前页右边 的总显示数
		int side_size = Max / 2;
		if(totalPage < Max){
			Max = totalPage;
		}
		if(curPage <= 0){
			throw new IllegalArgumentException();
		}
		else if(curPage > side_size && totalPage  - curPage >= side_size  ){ 
			leftPageCount = side_size;
			rightPageCount = side_size;
		}else{
			if(curPage < side_size){ // 如果左边小于5
				leftPageCount = curPage -1 ;
				//当前当前页右边页数 小于Max - 左边的页数的个数 那么右边页取totalPage  - curPage
				if(totalPage  - curPage < Max - leftPageCount ){
					rightPageCount = totalPage  - curPage;
				}
				else{
					rightPageCount = Max- leftPageCount - 1 ;
				}
			}
			
			//如果右边部分小于5的哈
			if(totalPage  - curPage < side_size){
				rightPageCount = totalPage  - curPage;
				if(curPage < Max - rightPageCount){
					leftPageCount = curPage - 1;
				}else{
					leftPageCount = Max - rightPageCount -1;
				}
			}
		}
		
		detailRihtSideAndLeftSide();
		/*System.out.println("当前页 " + curPage + "页");
		System.out.println(leftPageCount);
		System.out.println(rightPageCount);*/
	}
	
	
	public String createPageBar(String url , Map<String,String> param){
		if(url == null) return null;
		
		if(totalPage == 1 || totalPage == 0){
			return null;
		}
		
		StringBuffer urlAndParam = new StringBuffer("");		
		urlAndParam.append(url).append("?");
		
		if(param !=null && param.keySet().size() >0){
		for(String key :param.keySet()){
			//当前页直接跳过
			if("curPage".equals(key))
				continue;
			
			try{
			urlAndParam.append(key).append("=");
			if(param.get(key) != null){
				urlAndParam.append(param.get(key));
			}
			urlAndParam.append("&");
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		}else{
			
		}
		StringBuffer pageBarStr = new StringBuffer("");
		pageBarStr.append("<a href=\" " + urlAndParam.toString()+ "curPage=1\">首页</a>&nbsp;");
		
		/*if(curPage > 1){
			pageBarStr.append("<a href=\" " + urlAndParam.toString()+ "curPage=" + (curPage-1) +"\">上一页</a>");
			
		}else{
			//pageBarStr.append("<a class=\"disable\">上一页</a>");
		}*/
		
		if(leftSideBarNumber != null){
			for(int i=0 ; i < leftSideBarNumber.length; i++){
				pageBarStr.append("<a href=\" " + urlAndParam.toString()+ "curPage=" + leftSideBarNumber[i]+"\">" + leftSideBarNumber[i] + "</a>&nbsp;");
			}
		}
		pageBarStr.append("<span>"+ curPage +"</span>");
		
		if(rightSideBarNumber != null){
			for(int i=0 ; i < rightSideBarNumber.length; i++){
				pageBarStr.append("<a href=\" " + urlAndParam.toString()+ "curPage=" + rightSideBarNumber[i]+"\">" + rightSideBarNumber[i] + "</a>&nbsp;");
			}
		}
		
	/*	if(curPage < totalPage){
			pageBarStr.append("<a href=\" " + urlAndParam.toString()+ "curPage=" + (curPage+1) +"\">下一页</a>");
			
		}else{
			//pageBarStr.append("<a class=\"disable\">下一页</a>");
		}*/
		
		pageBarStr.append("<a href=\" " + urlAndParam.toString()+ "curPage=" + totalPage + "\">末页</a>&nbsp;");
		return pageBarStr.toString();
	}
	
	
	public String createPageBarNoFirstAndLastPage(String url , Map<String,String> param){
		if(url == null) return null;
		
		if(totalPage == 1 || totalPage == 0){
			return null;
		}
		StringBuffer urlAndParam = new StringBuffer("");
		
		urlAndParam.append(url).append("?");
		
		if(param !=null && param.keySet().size() >0){
		for(String key :param.keySet()){
			
			//当前页直接跳过
			if("curPage".equals(key))
				continue;
			
			urlAndParam.append(key).append("=").append(param.get(key)).append("&");
		}
		}else{
			
		}
		StringBuffer pageBarStr = new StringBuffer("");
		
		if(curPage > 1){
			pageBarStr.append("<a href=\" " + urlAndParam.toString()+ "curPage=" + (curPage-1) +"\">上一页</a>");
			
		}else{
			//pageBarStr.append("<a class=\"disable\">上一页</a>");
		}
		
		if(leftSideBarNumber != null){
			for(int i=0 ; i < leftSideBarNumber.length; i++){
				pageBarStr.append("<a href=\" " + urlAndParam.toString()+ "curPage=" + leftSideBarNumber[i]+"\">" + leftSideBarNumber[i] + "</a>");
			}
		}
		pageBarStr.append("<a class=\"curPage\">" + curPage +"</a>");
		
		if(rightSideBarNumber != null){
			for(int i=0 ; i < rightSideBarNumber.length; i++){
				pageBarStr.append("<a href=\" " + urlAndParam.toString()+ "curPage=" + rightSideBarNumber[i]+"\">" + rightSideBarNumber[i] + "</a>");
			}
		}
		
		if(curPage < totalPage){
			pageBarStr.append("<a href=\" " + urlAndParam.toString()+ "curPage=" + (curPage+1) +"\">下一页</a>");
			
		}else{
			//pageBarStr.append("<a class=\"disable\">下一页</a>");
		}
		
		return pageBarStr.toString();
	}
	
	public static void main(String[] args) {
		
		int x = 9;
		
		Page  p = new Page(21, 10, 200);
		System.out.println("totalPage : " + p.getTotalPage());
		p.style1();
		System.out.println("left: " + p.getLeftPageCount());
		System.out.println("right: " + p.getRightPageCount());
		
		p.style2();
		System.out.println("left: " + p.getLeftPageCount());
		System.out.println("right: " + p.getRightPageCount());
	}

	public String[] getLeftSideBarNumber() {
		return leftSideBarNumber;
	}

	public void setLeftSideBarNumber(String[] leftSideBarNumber) {
		this.leftSideBarNumber = leftSideBarNumber;
	}

	public String[] getRightSideBarNumber() {
		return rightSideBarNumber;
	}

	public void setRightSideBarNumber(String[] rightSideBarNumber) {
		this.rightSideBarNumber = rightSideBarNumber;
	}
		
	
}
