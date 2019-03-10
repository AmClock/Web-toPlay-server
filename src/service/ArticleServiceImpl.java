package service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import dao.ArticleDAO;
import dao.ContentDAO;
import dao.CountDAO;
import dao.RepliesDAO;
import util.PaginateUtil;
import vo.Article;
import vo.Content;
import vo.Count;
import vo.PageVO;
import vo.Reply;

public class ArticleServiceImpl implements ArticleService {

	private ArticleDAO articleDAO;

	public void setArticleDAO(ArticleDAO articleDAO) {
		this.articleDAO = articleDAO;
	}

	private ContentDAO contentDAO;

	public void setContentDAO(ContentDAO contentDAO) {
		this.contentDAO = contentDAO;
	}

	private CountDAO countDAO;

	public void setCountDAO(CountDAO countDAO) {
		this.countDAO = countDAO;
	}

	private RepliesDAO repliesDAO;

	public void setRepliesDAO(RepliesDAO repliesDAO) {
		this.repliesDAO = repliesDAO;
	}

	private PaginateUtil paginateUtil;

	public void setPaginateUtil(PaginateUtil paginateUtil) {
		this.paginateUtil = paginateUtil;
	}

	@Override
	public boolean writeArticle(Article article, int[] seqTmp, String[] imgTmp, String[] contentTmp) {
		// TODO Auto-generated method stub
		Boolean check = false;
		if (articleDAO.insertArticle(article) == 1) {
			for (int i = 0; i < seqTmp.length; i++) {
				Content content = new Content(seqTmp[i], contentTmp[i], imgTmp[i], article.getArticleNo());
				int result = contentDAO.insertContent(content);
			}
			check = true;
		}
		return check;
	}// writeArticle end

	@Override
	public Map<String, Object> articleDetail(int no , int userNo) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new ConcurrentHashMap<>();
		if(userNo !=0) {
			Count count = new Count(userNo,no);
			int num = countDAO.selectView(count) != 1 ? countDAO.insertView(count): 0;
		}
		map.put("article", articleDAO.seletOne(no));
		map.put("contents", contentDAO.selectList(no));
		map.put("views", countDAO.selectViewNum(no));
		return map;
	}// articleDetail end
	
	@Override
	public Map<String, Object> articleDetail(int no) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new ConcurrentHashMap<>();
		map.put("article", articleDAO.seletOne(no));
		map.put("contents", contentDAO.selectList(no));
		map.put("views", countDAO.selectViewNum(no));
		return map;
	}// articleDetail end
	
	@Override
	public Map<String, Object> likeFunction(Count count, Boolean clickFlag) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new ConcurrentHashMap<>();
		boolean check = 0 != countDAO.selectOneLike(count.getUserNo());
		if (clickFlag == true) {
			map.put("result", check ? countDAO.deleteLike(count.getUserNo()) : countDAO.insertLike(count));
		} else {
			map.put("result", check);
		}
		map.put("likeNum", countDAO.selectLikeCount(count.getArticleNo()));
		return map;
	}// likeFunction end

	@Override
	public int wirteReply(Reply reply) {
		// TODO Auto-generated method stub
		return repliesDAO.insertReply(reply);
	}

	@Override
	public Map<String, Object> listReply(int articleNo , int pageNo) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new ConcurrentHashMap<>();
		int numPage = 3;
		PageVO pageVO = new PageVO(pageNo , numPage , articleNo);
		int numBlock = 5;
		int total = repliesDAO.selectTotal(articleNo);
		String url = "/reply/page/";
		map.put("list",repliesDAO.selectList(pageVO));
		map.put("total", total);
		map.put("paginate", paginateUtil.getPaginate(pageNo, total, numPage, numBlock, url));
		return map;
	}

	@Override
	public int deletReply(int no) {
		// TODO Auto-generated method stub
		return repliesDAO.deleteReply(no);
	}

	@Override
	public int updateReply(Reply reply) {
		// TODO Auto-generated method stub
		return repliesDAO.updateReply(reply);
	}

	@Override
	public int deletArticle(int no) {
		// TODO Auto-generated method stub
		int result = 0;
		if(contentDAO.deleteArticleContent(no)==1) {
			result = articleDAO.deletOne(no);
		}
		return result;
	}

	@Override
	public void updateArticle(Article article, int[] seqTmp, String[] imgTmp, String[] contentTmp, int[] noTmp) {
		// TODO Auto-generated method stub
		int result = 0;
		for(int i : noTmp) {
			result = contentDAO.deleteContent(i);
		}
		if(result == 1 && articleDAO.updateArticle(article) == 1) {
			for (int i = 0; i < seqTmp.length; i++) {
				Content content = new Content(seqTmp[i], contentTmp[i], imgTmp[i], article.getArticleNo());
				contentDAO.insertContent(content);
			}
		}
	}//updateArticle end

	@Override
	public Map<String, Object> mainList(int pageNo , String order , int searchNo , int userNo) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new ConcurrentHashMap<>();
		int numPage = 12;
		PageVO pageVO = new PageVO(pageNo , numPage , order , searchNo , userNo);
		int total = 100;
		int numBlock = 5;
		String url ="/main/page/";
		map.put("list", articleDAO.selectList(pageVO));
		map.put("paginate",paginateUtil.getPaginate(pageNo, total, numPage, numBlock, url));
		return map;
	}

	@Override
	public List<Article> searchTitle(String title) {
		// TODO Auto-generated method stub
		title = "%"+title+"%";
		return articleDAO.selectTitle(title);
	}//searchTitle end
	
	
	
	

}
