package controller;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import service.UserService;
import util.FileRenameUtil;
import util.ResizeImageUtil;
import vo.User;

@Controller
public class UserController {

	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private FileRenameUtil fileRenameUtil;
	
	public void setFileRenameUtil(FileRenameUtil fileRenameUtil) {
		this.fileRenameUtil = fileRenameUtil;
	}
	
	private ResizeImageUtil resizeImageUtil;
	
	public void setResizeImageUtil(ResizeImageUtil resizeImageUtil) {
		this.resizeImageUtil = resizeImageUtil;
	}
	
	@RequestMapping(value = { "/", "/main" }, method = RequestMethod.GET)
	public String goMain() {
		return "main";
	}// goMain end

	@RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public String goSignUp(@RequestHeader String referer, Model model) {
		model.addAttribute("referer", referer);
		return "signUp";
	}// goSignUp end
	
	@RequestMapping(value = "/signUp", method = RequestMethod.PUT)
	public String update(User user , @RequestHeader String referer , HttpSession session) {
		if(userService.userUpdate(user)) {
			System.out.println("referer :: " + referer);
		}
		return "redirect:" + referer;
	}// goSignUp end
	
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public String signUpUser(User user, String referer) {
		String url = "signUp";
		if (userService.signUp(user) == 1) {
			url = "redirect:" + referer;
		}
		return url;
	}

	@RequestMapping(value="/ajax/check/id/{id}" , method = RequestMethod.GET)
	@ResponseBody
	public String ajaxCheckId(@PathVariable String id) {
		return "{\"result\":"+ userService.checkId(id)+"}";
	}
	
	@RequestMapping(value="/ajax/check/name/{name}",method=RequestMethod.GET)
	@ResponseBody
	public String ajaxCheckName(@PathVariable String name) {
		return "{\"result\":"+userService.checkName(name)+"}";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String goLogin(@RequestHeader String referer, Model model) {
		model.addAttribute("referer", referer);
		return "login";
	}// goSignUp end

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String referer, User user, HttpSession session) {
		session.setAttribute(User.LOGIN, userService.login(user));
		return "redirect:" + referer;
	}// login end
	
	
	@RequestMapping(value="/login",method=RequestMethod.DELETE)
	public String login(HttpSession session) {
		session.invalidate();
		return "redirect:/main";
	}

	@RequestMapping(value="/ajax/upload",method=RequestMethod.POST , produces="application/json;charset=UTF-8")
	@ResponseBody
	public String uploadProfile(HttpServletRequest request, String type, MultipartFile uploadImg) {
		ServletContext sc = request.getServletContext();
		
		String uploadPath = sc.getRealPath("img/upload");
		String profilePath = sc.getRealPath("img/profile");
		
		File file = new File(uploadPath+File.separator+uploadImg.getOriginalFilename()); 
		
		file = fileRenameUtil.rename(file);		
		
		String src = "";
	
		try {
			uploadImg.transferTo(file);
			switch(type) {
			case "P" : 
				resizeImageUtil.resize(file.getAbsolutePath(),profilePath+File.separator+file.getName(), 200);
				break;
			case "B" : 
				src+="upload/";
				break;
			}//switch end
			src+= file.getName();
			return "{\"src\":\""+src+"\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "에러";
		}
	}//uploadProfile end
	
	@RequestMapping(value="/update" , method = RequestMethod.GET)
	public String update(Model m) {
		m.addAttribute("update","test");
		return "signUp";
	}
	
}// UserController end
