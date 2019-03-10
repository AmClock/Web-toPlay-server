package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import vo.PageVO;
import vo.Reply;

public class RepliesDAOImpl implements RepliesDAO {
	private SqlSession session;

	public void setSession(SqlSession session) {
		this.session = session;
	}

	@Override
	public int insertReply(Reply reply) {
		// TODO Auto-generated method stub
		return session.insert("replies.insertReply", reply);
	}

	@Override
	public List<Reply> selectList(PageVO pageVO) {
		// TODO Auto-generated method stub
		return session.selectList("replies.selectList", pageVO);
	}

	@Override
	public int selectTotal(int articleNo) {
		// TODO Auto-generated method stub
		return session.selectOne("replies.selectTotal", articleNo);
	}

	@Override
	public int deleteReply(int no) {
		// TODO Auto-generated method stub
		return session.delete("replies.deleteReply",no);
	}

	@Override
	public int updateReply(Reply reply) {
		// TODO Auto-generated method stub
		return session.update("replies.updateReply",reply);
	}

}// RepliesDAOImpl end
