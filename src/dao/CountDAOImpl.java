package dao;

import org.apache.ibatis.session.SqlSession;

import vo.Content;
import vo.Count;

public class CountDAOImpl implements CountDAO {
	private SqlSession session;

	public void setSession(SqlSession session) {
		this.session = session;
	}

	@Override
	public int selectOneLike(int no) {
		// TODO Auto-generated method stub
		return session.selectOne("count.selectOneLike",no);
	}

	@Override
	public Boolean insertLike(Count count) {
		// TODO Auto-generated method stub
		return session.insert("count.insertLike",count)==1 ? true : false;
	}

	@Override
	public Boolean deleteLike(int no) {
		// TODO Auto-generated method stub
		return session.delete("count.deleteLike",no)==1 ? false : true;
	}

	@Override
	public int selectLikeCount(int no) {
		// TODO Auto-generated method stub
		return session.selectOne("count.selectLikeCount",no);
	}

	@Override
	public int selectView(Count count) {
		// TODO Auto-generated method stub
		return session.selectOne("count.selectView",count);
	}

	@Override
	public int insertView(Count count) {
		// TODO Auto-generated method stub
		return session.insert("count.insertView",count);
	}

	@Override
	public int selectViewNum(int articleNo) {
		// TODO Auto-generated method stub
		return session.selectOne("count.selectViewNum",articleNo);
	}
	
}
