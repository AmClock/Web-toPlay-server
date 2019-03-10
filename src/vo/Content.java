package vo;

public class Content {

	private int no, articleNo, seq;
	private String content, img;

	public Content() {
		// TODO Auto-generated constructor stub
	}
	
	public Content(int seq , String content , String img ) {
		// TODO Auto-generated constructor stub
		this.seq = seq;
		this.content = content;
		this.img = img;
	}
	
	public Content(int seq , String content , String img , int articleNo) {
		// TODO Auto-generated constructor stub
		this.seq = seq;
		this.content = content;
		this.img = img;
		this.articleNo = articleNo;
	}
	
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getArticleNo() {
		return articleNo;
	}

	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
}
