package vo;

import java.sql.Timestamp;

public class Count {

	private int no, userNo, articleNo;
	private String type;
	private Timestamp regdate;

	public Count() {
		// TODO Auto-generated constructor stub
	}

	public Count(int userNo, int articleNo) {
		// TODO Auto-generated constructor stub
		this.userNo = userNo;
		this.articleNo = articleNo;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public int getArticleNo() {
		return articleNo;
	}

	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getRegdate() {
		return regdate;
	}

	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}

}
