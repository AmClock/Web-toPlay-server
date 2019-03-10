package service;

import java.util.List;
import java.util.Map;

import vo.Article;
import vo.Content;
import vo.Count;
import vo.Reply;

public interface ArticleService {

	public boolean writeArticle(Article article, int[] seqTmp, String[] imgTmp, String[] contentTmp);

	public Map<String, Object> articleDetail(int no, int userNo);

	public Map<String, Object> articleDetail(int no);

	public Map<String, Object> likeFunction(Count count, Boolean clickFlag);

	public int wirteReply(Reply reply);

	public Map<String, Object> listReply(int articleNo, int pageNo);

	public int deletReply(int no);

	public int updateReply(Reply reply);

	public int deletArticle(int no);

	public void updateArticle(Article article, int[] seqTmp, String[] imgTmp, String[] contentTmp, int[] noTmp);
	
	public Map<String , Object> mainList(int pageNo , String order , int searchNo , int userNo);
	
	public List<Article> searchTitle(String title);
}
