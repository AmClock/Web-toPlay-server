package dao;

import java.util.List;

import vo.Article;
import vo.PageVO;

public interface ArticleDAO {

	public int insertArticle(Article article);

	public Article seletOne(int no);

	public int deletOne(int no);

	public int updateArticle(Article article);

	public List<Article> selectList(PageVO pageVO);

	public List<Article> selectTitle(String title);

}
