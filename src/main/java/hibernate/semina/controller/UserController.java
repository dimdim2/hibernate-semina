package hibernate.semina.controller;

import hibernate.semina.common.PaginatedListImpl;
import hibernate.semina.generic.Pageable;
import hibernate.semina.model.User;
import hibernate.semina.service.UserGroupService;
import hibernate.semina.service.UserService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.displaytag.pagination.PaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserGroupService userGroupService;

	@RequestMapping(value = "/user/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm(HttpSession session) {
		ModelAndView mav = new ModelAndView("user/create");
		mav.addObject("user", new User());
		mav.addObject("groups", userGroupService.find());
		return mav;
	}

	@RequestMapping(value = "/user/create.htm", method = RequestMethod.POST)
	public ModelAndView create(@ModelAttribute User user,
			@RequestParam(value="functionNames", required=false) String[] functionNames,
			@RequestParam(value="functionUrls", required=false) String[] functionUrls,
			@RequestParam(value="functionTypes", required=false) String[] functionTypes,
			SessionStatus sessionStatus) {

		user.setCreateTime(new Date());
		userService.create(user);
		sessionStatus.setComplete();

		return new ModelAndView("redirect:/user/detail.htm?id=" + user.getId());
	}

	@RequestMapping("/user/delete.json")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam("id") String id) {

		Map<String, Object> result = new HashMap<String, Object>();

		User user = userService.findById(id);
		if (user != null) {
			userService.delete(user);
			result.put("isSuccess", true);
			result.put("resultMsg", String.format("Delete User [ID:%s, Name:%s]", id, user.getName()));

		} else {
			result.put("isSuccess", false);
			result.put("resultMsg", String.format("Not Exist User [ID:%s]", id));
		}

		return result;
	}

	@RequestMapping("/user/detail.htm")
	public ModelAndView detail(@RequestParam("id") String id) throws Exception {
		ModelAndView mav = new ModelAndView("user/detail");
		mav.addObject("user", userService.findById(id));
		return mav;
	}

	@RequestMapping("/user/search.htm")
	public ModelAndView search(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rowPerPage", defaultValue = "10") int rowPerPage,
			@RequestParam(value = "name", required = false) String name) {

		PaginatedList users = null;

		User user = new User();
		if (StringUtils.isNotEmpty(name)) {
			user.setName(name);
		}

		List<User> list = userService.find(user, new Pageable(page, rowPerPage));
		users = new PaginatedListImpl(list, page, userService.count(user), rowPerPage);

		ModelAndView mav = new ModelAndView("user/search");
		mav.addObject("users", users);
		return mav;
	}

	@RequestMapping(value = "/user/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam("id") String id) throws Exception {
		ModelAndView mav = new ModelAndView("user/update");
		mav.addObject("user", userService.findById(id));
		return mav;
	}

	@RequestMapping(value = "/user/update.htm", method = RequestMethod.POST)
	public ModelAndView update(
			@ModelAttribute User user,
			@RequestParam(value="functionNames", required=false) String[] functionNames,
			@RequestParam(value="functionUrls", required=false) String[] functionUrls,
			@RequestParam(value="functionTypes", required=false) String[] functionTypes,
			SessionStatus sessionStatus) {

		user.setUpdateTime(new Date());
		userService.update(user);
		sessionStatus.setComplete();

		ModelAndView mav = new ModelAndView("redirect:/user/detail.htm?id=" + user.getId());
		mav.addObject("groups", userGroupService.find());
		return mav;
	}
}
