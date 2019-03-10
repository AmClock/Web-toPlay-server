package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import vo.Content;

public class ContentDAOImpl implements ContentDAO {
	private SqlSession session;

	public void setSession(SqlSession session) {
		this.session = session;
	}

	@Override
	public int insertContent(Content content) {
		// TODO Auto-generated method stub
		return session.insert("content.insertContent", content);
	}

	@Override
	public List<Content> selectList(int i) {
		// TODO Auto-generated method stub
		return session.selectList("content.selectList",i);
	}

	@Override
	public int deleteContent(int no) {
		// TODO Auto-generated method stub
		return session.delete("content.deleteContent",no);
	}

	@Override
	public int deleteArticleContent(int no) {
		// TODO Auto-generated method stub
		return session.delete("content.deleteArticleContent",no);
	}

}
