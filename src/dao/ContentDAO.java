package dao;

import java.util.List;

import vo.Content;

public interface ContentDAO {
	
	public int insertContent(Content content);

	public List<Content> selectList(int no);
	
	public int deleteContent(int no);
	
	public int deleteArticleContent(int no);
}
