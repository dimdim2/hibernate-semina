package hibernate.semina.controller;

import hibernate.semina.auth.Authentication;
import hibernate.semina.auth.AuthenticationManager;
import hibernate.semina.auth.exception.AuthenticationException;
import hibernate.semina.model.User;
import hibernate.semina.model.UserGroup;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@RequestMapping(value = "/home/login.htm", method = RequestMethod.GET)
	protected String loginForm() {
		return "home/login";
	}

	@RequestMapping(value = "/home/login.json", method = RequestMethod.POST)
	@ResponseBody
	protected Map<Object, Object> login(
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "password", required = false) String password,
			HttpSession session) throws Exception {

		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			Authentication authentication = authenticationManager.authenticate(userId, password);
			User user = authentication.getUser();
			UserGroup userGroup = user.getGroup();

			session.setAttribute(SessionAttrName.AUTHENTICATION, authentication);
			session.setAttribute(SessionAttrName.LOGIN_ID, userId);
			session.setAttribute(SessionAttrName.LOGIN_USER, user);
			session.setAttribute(SessionAttrName.LOGIN_GROUP, userGroup);
			result.put("success", true);

		} catch (AuthenticationException e) {
			result.put("success", false);

			Map<Object, Object> errors = new HashMap<Object, Object>();
			errors.put("reason", "Login failed. Try again.");
			result.put("errors", errors);
		}

		return result;
	}

	@RequestMapping("/home/logout.htm")
	protected ModelAndView logout(HttpSession session) throws Exception {
		session.invalidate();
		return new ModelAndView("home/login");
	}
}
