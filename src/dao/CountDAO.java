package dao;

import vo.Content;
import vo.Count;

public interface CountDAO {
	public int selectOneLike(int no);

	public Boolean insertLike(Count count);

	public Boolean deleteLike(int no);

	public int selectLikeCount(int no);
	
	public int selectView(Count count);
	
	public int insertView(Count count);
	
	public int selectViewNum(int articleNo);
}
