package dao;

import java.util.List;

import vo.PageVO;
import vo.Reply;

public interface RepliesDAO {
	public int insertReply(Reply reply);

	public List<Reply> selectList(PageVO pageVO);

	public int selectTotal(int articleNo);

	public int deleteReply(int no);

	public int updateReply(Reply reply);
}
