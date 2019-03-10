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

@Controller
public class ArticleController {

	private FileRenameUtil fileRenameUtil;

	public void setFileRenameUtil(FileRenameUtil fileRenameUtil) {
		this.fileRenameUtil = fileRenameUtil;
	}

	private ResizeImageUtil resizeImageUtil;

	public void setResizeImageUtil(ResizeImageUtil resizeImageUtil) {
		this.resizeImageUtil = resizeImageUtil;
	}

	private ArticleService articleService;

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	@RequestMapping(value = "/detail/{no}", method = RequestMethod.GET)
	public String goDetail(@PathVariable int no, Model model,HttpSession session) {
		int userNo = 0 ;
		if((session.getAttribute("loginUser") != null)) {
			User user = (User)session.getAttribute("loginUser");
			userNo = user.getNo();
		}
		model.addAllAttributes(articleService.articleDetail(no , userNo));
		return "detail";
	}// goDetail end

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String goWrite() {
		return "form";
	}// goWrite end

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String writeArticle(Article article, int[] seqTmp, String[] imgTmp, String[] contentTmp) {
		articleService.writeArticle(article, seqTmp, imgTmp, contentTmp);
		return "redirect:/main";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.PUT)
	public String updateArticle(Article article, int[] seqTmp, String[] imgTmp, String[] contentTmp , int[] noTmp , String referer) {
		articleService.updateArticle(article, seqTmp, imgTmp, contentTmp, noTmp);
		return "redirect:"+referer;
	}//update
	
	@RequestMapping(value = "/delete/{no}", method = RequestMethod.DELETE)
	public String deleteArticle(@PathVariable int no) {
		articleService.deletArticle(no);
		return "redirect:/main";
	}// goWrite end
	
	@RequestMapping(value = "/update/{no}", method = RequestMethod.GET)
	public String updateArticle(@PathVariable int no , Model model , @RequestHeader String referer) {
		model.addAllAttributes(articleService.articleDetail(no));
		model.addAttribute("referer" , referer);		
		return "form";
	}// updateArticle end
	
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
			return "에러";
		}
	}//uploadImg end
	
	@RequestMapping(value = "/ajax/like/{clickFlag}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ajaxLike(@PathVariable Boolean clickFlag , Count count) {
		return articleService.likeFunction(count , clickFlag);
	}
	
	@RequestMapping(value="/ajax/write/reply",method=RequestMethod.POST)
	@ResponseBody
	public int writeReply(Reply reply) {
		return articleService.wirteReply(reply);
	}//wrteReply ebd
	
	
	@RequestMapping(value="/ajax/list/reply/{pageNo}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listReply(@PathVariable int pageNo , int articleNo) {
		return articleService.listReply(articleNo , pageNo);
	}//listReply ebd
	
	@RequestMapping(value="/ajax/delete/reply",method=RequestMethod.POST)
	@ResponseBody
	public int  deleteReply(int no) {
		return articleService.deletReply(no);
	}//listReply ebd
	
	
	@RequestMapping(value="/ajax/update/reply",method=RequestMethod.POST)
	@ResponseBody
	public int  updateReply(Reply reply) {
		return articleService.updateReply(reply);
	}//listReply ebd
	
	
	
	@RequestMapping(value="/ajax/list/article",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  articleList(int no , String order , int searchNo , int userNo) {
		return articleService.mainList(no , order , searchNo , userNo);
	}//listReply ebd
	

	@RequestMapping(value="/ajax/search/title",method=RequestMethod.POST)
	@ResponseBody
	public List<Article> ajaxSearchTitle(String title){
		return articleService.searchTitle(title);
	}
}// ArticleController end
