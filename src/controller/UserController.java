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

/**
 * @file UserController.java \n
 * @brief user class \n
 * @author park \n
 */
@Controller
public class UserController {

	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}// setUserService end

	private FileRenameUtil fileRenameUtil;

	public void setFileRenameUtil(FileRenameUtil fileRenameUtil) {
		this.fileRenameUtil = fileRenameUtil;
	}// setFileRenameUtil end

	private ResizeImageUtil resizeImageUtil;

	public void setResizeImageUtil(ResizeImageUtil resizeImageUtil) {
		this.resizeImageUtil = resizeImageUtil;
	}// setResizeImageUtil end

	/**
	 * @name goMain \n
	 * @brief 메인 페이지 이동 함수 \n
	 * @return String \n
	 * @author park \n
	 * @version 1.0 \n
	 * @see None \n
	 */
	@RequestMapping(value = { "/", "/main" }, method = RequestMethod.GET)
	public String goMain() {
		return "main";
	}// goMain end

	/**
	 * @name goSignUp \n
	 * @brief 가입 페이지 이동 함수 \n
	 * @param String referer \n
	 * @params Model model \n
	 * @return String \n
	 * @author park \n
	 * @version 1.0 \n
	 * @see None \n
	 */
	@RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public String goSignUp(@RequestHeader String referer, Model model) {
		model.addAttribute("referer", referer);
		return "signUp";
	}// goSignUp end

	/**
	 * @name update \n
	 * @brief 회원정보 수정 함수 \n
	 * @param String referer \n
	 * @params HttpSession session \n
	 * @params User user \n
	 * @return String \n
	 * @author park \n
	 * @version 1.0 \n
	 * @see None \n
	 */
	@RequestMapping(value = "/signUp", method = RequestMethod.PUT)
	public String update(User user, @RequestHeader String referer, HttpSession session) {
		if (userService.userUpdate(user)) {
			System.out.println("referer :: " + referer);
		}
		return "redirect:" + referer;
	}// goSignUp end

	/**
	 * @name signUpUser \n
	 * @brief 회원정보 가입 기능 함수 (insert) \n
	 * @param String referer \n
	 * @params User user \n
	 * @return String \n
	 * @author park \n
	 * @version 1.0 \n
	 * @see None \n
	 */
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public String signUpUser(User user, String referer) {
		String url = "signUp";
		if (userService.signUp(user) == 1) {
			url = "redirect:" + referer;
		}
		return url;
	}

	/**
	 * @name ajaxCheckId \n
	 * @brief 아이디 체크 함수 \n
	 * @param String id \n
	 * @return String \n
	 * @author park \n
	 * @version 1.0 \n
	 * @see None \n
	 */
	@RequestMapping(value = "/ajax/check/id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String ajaxCheckId(@PathVariable String id) {
		return "{\"result\":" + userService.checkId(id) + "}";
	}

	/**
	 * @name ajaxCheckName \n
	 * @brief 닉네임 체크 함수 \n
	 * @param String id \n
	 * @return String \n
	 * @author park \n
	 * @version 1.0 \n
	 * @see None \n
	 */
	@RequestMapping(value = "/ajax/check/name/{name}", method = RequestMethod.GET)
	@ResponseBody
	public String ajaxCheckName(@PathVariable String name) {
		return "{\"result\":" + userService.checkName(name) + "}";
	}

	/**
	 * @name goLogin \n
	 * @brief 로그인 페이지 이동 함수 \n
	 * @param String referer \n
	 * @param Model  model \n
	 * @return String \n
	 * @author park \n
	 * @version 1.0 \n
	 * @see None \n
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String goLogin(@RequestHeader String referer, Model model) {
		model.addAttribute("referer", referer);
		return "login";
	}// goSignUp end

	/**
	 * @name login \n
	 * @brief 로그인 페이지 이동 함수 \n
	 * @param String      referer \n
	 * @param User        user \n
	 * @param HttpSession session \n
	 * @return String \n
	 * @author park \n
	 * @version 1.0 \n
	 * @see None \n
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String referer, User user, HttpSession session) {
		session.setAttribute(User.LOGIN, userService.login(user));
		return "redirect:" + referer;
	}// login end

	/**
	 * @name login \n
	 * @brief 로그아웃 함수\n
	 * @param HttpSession session \n
	 * @return String \n
	 * @author park \n
	 * @version 1.0 \n
	 * @see None \n
	 */
	@RequestMapping(value = "/login", method = RequestMethod.DELETE)
	public String login(HttpSession session) {
		session.invalidate();
		return "redirect:/main";
	}

	/**
	 * @name uploadProfile \n
	 * @brief 프로필 이미지 저장 및 리사이즈 함수\n
	 * @param HttpServletRequest request \n
	 * @param String             type \n
	 * @param MultipartFile      uploadImg \n
	 * @return String \n
	 * @author park \n
	 * @version 1.0 \n
	 * @see None \n
	 */
	@RequestMapping(value = "/ajax/upload", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String uploadProfile(HttpServletRequest request, String type, MultipartFile uploadImg) {
		ServletContext sc = request.getServletContext();

		String uploadPath = sc.getRealPath("img/upload");
		String profilePath = sc.getRealPath("img/profile");

		File file = new File(uploadPath + File.separator + uploadImg.getOriginalFilename());

		file = fileRenameUtil.rename(file);

		String src = "";

		try {
			uploadImg.transferTo(file);
			switch (type) {
			case "P":
				resizeImageUtil.resize(file.getAbsolutePath(), profilePath + File.separator + file.getName(), 200);
				break;
			case "B":
				src += "upload/";
				break;
			}// switch end
			src += file.getName();
			return "{\"src\":\"" + src + "\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "�뿉�윭";
		}
	}// uploadProfile end

	/**
	 * @name update \n
	 * @brief 업데이트 페이지로 이동하는 함수\n
	 * @param Model m \n
	 * @return String \n
	 * @author park \n
	 * @version 1.0 \n
	 * @see None \n
	 */
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(Model m) {
		m.addAttribute("update", "test");
		return "signUp";
	}

}// UserController end
