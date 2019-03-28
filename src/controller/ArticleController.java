package controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import service.ArticleService;
import util.FileRenameUtil;
import util.ResizeImageUtil;
import vo.Article;
import vo.Content;
import vo.Count;
import vo.Reply;
import vo.User;

/**
 * @file ArticleController.java \n
 * @brief article class \n
 * @author park \n
 */
@Controller
public class ArticleController {

	private FileRenameUtil fileRenameUtil;

	public void setFileRenameUtil(FileRenameUtil fileRenameUtil) {
		this.fileRenameUtil = fileRenameUtil;
	}// setFileRenameUtil end

	private ResizeImageUtil resizeImageUtil;

	public void setResizeImageUtil(ResizeImageUtil resizeImageUtil) {
		this.resizeImageUtil = resizeImageUtil;
	}// setResizeImageUtil end

	private ArticleService articleService;

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}// setArticleService end

	/**
	 * @name goDetail \n
	 * @brief 디테일 페이지로 이동하기 위한 메서드 \n
	 * @return String \n
	 * @author park \n
	 * @version 1.0 \n
	 * @see None \n
	 */
	@RequestMapping(value = "/detail/{no}", method = RequestMethod.GET)
	public String goDetail(@PathVariable int no, Model model, HttpSession session) {
		int userNo = 0;
		if ((session.getAttribute("loginUser") != null)) {
			User user = (User) session.getAttribute("loginUser");
			userNo = user.getNo();
		}
		model.addAllAttributes(articleService.articleDetail(no, userNo));
		return "detail";
	}// goDetail end

	/**
	 * @name goWrite \n
	 * @brief 버튼을 클릭시 FORM 으로 이동 \n
	 * @return String \n
	 * @author park \n
	 * @version 1.0 \n
	 * @see None \n
	 */
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String goWrite() {
		return "form";
	}// goWrite end

	/**
	 * @name writeArticle \n
	 * @brief 게시판의 insert 함수 \n
	 * @param seqTmp[] ==> 컨텐츠 순서 \n
	 * @param imgTmp[] ==> 이미지 이름 \n
	 * @param contentTmp[] ==> 컨텐츠 \n
	 * @return String \n
	 * @author park \n
	 * @version 1.0 \n
	 * @see None \n
	 */
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String writeArticle(Article article, int[] seqTmp, String[] imgTmp, String[] contentTmp) {
		articleService.writeArticle(article, seqTmp, imgTmp, contentTmp);
		return "redirect:/main";
	}// writeArticle end

	/**
	 * @name updateArticle \n
	 * @brief 게시판 업데이트 함수 \n
	 * @param referer ==> 전 페이지 URL \n
	 * @param         seqTmp[] ==> 컨텐츠 순서 \n
	 * @param         noTmp[] ==> 컨텐츠 번호 \n
	 * @param         imgTmp[] ==> 이미지 이름 \n
	 * @param         contentTmp[] ==> 컨텐츠 \n
	 * @return String \n
	 * @author park
	 * @version 1.0
	 * @see None
	 */
	@RequestMapping(value = "/write", method = RequestMethod.PUT)
	public String updateArticle(Article article, int[] seqTmp, String[] imgTmp, String[] contentTmp, int[] noTmp,
			String referer) {
		articleService.updateArticle(article, seqTmp, imgTmp, contentTmp, noTmp);
		return "redirect:" + referer;
	}// update

	/**
	 * @name deleteArticle \n
	 * @brief 게시판 삭제 함수 \n
	 * @param no ==> 계시판 번호 \n
	 * @return String \n
	 * @author park
	 * @version 1.0
	 * @see None
	 */
	@RequestMapping(value = "/delete/{no}", method = RequestMethod.DELETE)
	public String deleteArticle(@PathVariable int no) {
		articleService.deletArticle(no);
		return "redirect:/main";
	}// goWrite end

	/**
	 * @name updateArticle \n
	 * @brief 업데이트 페이지로 이동 , 번호의 페이지에서 컨텐츠를 불러준다. \n
	 * @param no      ==> 페이지번호 \n
	 * @param referer ==> 전 페이지 URL \n
	 * @param model   \n
	 * @return String \n
	 * @author park \n
	 * @version 1.0 \n
	 * @see None \n
	 */
	@RequestMapping(value = "/update/{no}", method = RequestMethod.GET)
	public String updateArticle(@PathVariable int no, Model model, @RequestHeader String referer) {
		model.addAllAttributes(articleService.articleDetail(no));
		model.addAttribute("referer", referer);
		return "form";
	}// updateArticle end

	/**
	 * @name uploadImg \n
	 * @brief 이미지 업로드 및 이미지 리사이즈 \n
	 * @param request   \n
	 * @param type      \n
	 * @param uploadImg \n
	 * @return String \n
	 * @author park \n
	 * @version 1.0 \n
	 * @see None \n
	 */
	@RequestMapping(value = "/ajax/upload/img", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String uploadImg(HttpServletRequest request, String type, MultipartFile uploadImg) {
		ServletContext sc = request.getServletContext();

		String uploadPath = sc.getRealPath("img/upload");

		String articlePath = sc.getRealPath("img/article");

		File file = new File(uploadPath + File.separator + uploadImg.getOriginalFilename());

		file = fileRenameUtil.rename(file);

		String src = "";

		try {
			uploadImg.transferTo(file);
			switch (type) {
			case "A":
				resizeImageUtil.resize(file.getAbsolutePath(), articlePath + File.separator + file.getName(), 398, 398);
				break;
			case "B":
				src += "upload/";
				break;
			}// switch end
			src += file.getName();
			return "{\"src\":\"" + src + "\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR";
		}
	}// uploadImg end

	/**
	 * @name ajaxLike \n
	 * @brief 버튼을 클릭시 like 기능 처리 \n
	 * @param clickFlag ==> 클릭유무 \n
	 * @param count     ==> count 객체 :: insert시키위한 것 \n
	 * @return Map \n
	 * @author park \n
	 * @version 1.0 \n
	 * @see None \n
	 */
	@RequestMapping(value = "/ajax/like/{clickFlag}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ajaxLike(@PathVariable Boolean clickFlag, Count count) {
		return articleService.likeFunction(count, clickFlag);
	}// ajaxLike end

	/**
	 * @name writeReply \n
	 * @brief 리플을 쓰느 함수 \n
	 * @param Reply reply \n
	 * @return int \n
	 * @author park \n
	 * @version 1.0 \n
	 * @see None \n
	 */
	@RequestMapping(value = "/ajax/write/reply", method = RequestMethod.POST)
	@ResponseBody
	public int writeReply(Reply reply) {
		return articleService.wirteReply(reply);
	}// wrteReply ebd

	/**
	 * @name listReply \n
	 * @brief 리플의 리스트를 불러오는 곳 \n
	 * @param int pageNo \n
	 * @param int articleNo \n
	 * @return Map \n
	 * @author park \n
	 * @version 1.0 \n
	 * @see None \n
	 */
	@RequestMapping(value = "/ajax/list/reply/{pageNo}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listReply(@PathVariable int pageNo, int articleNo) {
		return articleService.listReply(articleNo, pageNo);
	}// listReply ebd

	/**
	 * @name deleteReply \n
	 * @brief 리플을 삭제하는 함수 \n
	 * @param int no \n
	 * @return int \n
	 * @author park \n
	 * @version 1.0 \n
	 * @see None \n
	 */
	@RequestMapping(value = "/ajax/delete/reply", method = RequestMethod.POST)
	@ResponseBody
	public int deleteReply(int no) {
		return articleService.deletReply(no);
	}// listReply ebd

	/**
	 * @name updateReply \n
	 * @brief 리플을 수정하는 함수 \n
	 * @param Reply reply \n
	 * @return int \n
	 * @author park \n
	 * @version 1.0 \n
	 * @see None \n
	 */
	@RequestMapping(value = "/ajax/update/reply", method = RequestMethod.POST)
	@ResponseBody
	public int updateReply(Reply reply) {
		return articleService.updateReply(reply);
	}// listReply ebd

	/**
	 * @name articleList \n
	 * @brief 게시판 리스트 불러오는 함수 \n
	 * @param        int no \n
	 * @param String order \n
	 * @param        int searchNo \n
	 * @param        int userNo \n
	 * @return Map \n
	 * @author park \n
	 * @version 1.0 \n
	 * @see None \n
	 */
	@RequestMapping(value = "/ajax/list/article", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> articleList(int no, String order, int searchNo, int userNo) {
		return articleService.mainList(no, order, searchNo, userNo);
	}// listReply ebd

	/**
	 * @name ajaxSearchTitle \n
	 * @brief 검색 자동완성 기능의 함수\n
	 * @param String title \n
	 * @return List\n
	 * @author park \n
	 * @version 1.0 \n
	 * @see None \n
	 */
	@RequestMapping(value = "/ajax/search/title", method = RequestMethod.POST)
	@ResponseBody
	public List<Article> ajaxSearchTitle(String title) {
		return articleService.searchTitle(title);
	}

}// ArticleController end
