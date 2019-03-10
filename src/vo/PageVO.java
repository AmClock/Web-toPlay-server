package vo;

public class PageVO {

	private int start, end, articleNo, userNo;
	private String order;

	public PageVO() {
		// TODO Auto-generated constructor stub
	}

	public PageVO(int page, int numPage) {
		end = page * numPage;
		start = end - (numPage - 1);
	}

	public PageVO(int page, int numPage, int articleNo) {
		end = page * numPage;
		start = end - (numPage - 1);
		this.articleNo = articleNo;
	}

	public PageVO(int page, int numPage, String order, int articleNo) {
		end = page * numPage;
		start = end - (numPage - 1);
		this.order = order.equals("new") ? "a.regdate desc" : "l.likes desc";
		this.articleNo = articleNo;
	}
	
	public PageVO(int page, int numPage, String order, int articleNo , int userNo) {
		end = page * numPage;
		start = end - (numPage - 1);
		this.order = order.equals("new") ? "a.regdate desc" : "l.likes desc";
		this.articleNo = articleNo;
		this.userNo = userNo;
	}
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	// ������
	private int gameNo;

	public int getGameNo() {
		return gameNo;
	}

	public void setGameNo(int gameNo) {
		this.gameNo = gameNo;
	}

	public int getArticleNo() {
		return articleNo;
	}

	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

}
