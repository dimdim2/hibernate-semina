package hibernate.semina.controller;

import hibernate.semina.common.PaginatedListImpl;
import hibernate.semina.generic.Pageable;
import hibernate.semina.model.GroupAuth;
import hibernate.semina.model.Role;
import hibernate.semina.model.UserGroup;
import hibernate.semina.service.RoleService;
import hibernate.semina.service.UserGroupService;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
@SessionAttributes("userGroup")
public class UserGroupController {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/usergroup/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm(HttpSession session) {
		UserGroup userGroup = new UserGroup();
		List<Role> roles = roleService.find();
		for (Role role : roles) {
			userGroup.addAuthority(new GroupAuth(role, "N"));
		}

		ModelAndView mav = new ModelAndView("usergroup/create");
		mav.addObject("userGroup", new UserGroup());
		return mav;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/usergroup/create.htm", method = RequestMethod.POST)
	public ModelAndView create(UserGroup userGroup, HttpServletRequest request, SessionStatus sessionStatus) {

		userGroup.setCreateTime(new Date());

		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameterName = (String)parameterNames.nextElement();
			String parameterValue = request.getParameter(parameterName);
			if (StringUtils.isNumeric(parameterName)) {
				Long roleId = Long.valueOf(parameterName);
				userGroup.addAuthority(new GroupAuth(roleId, parameterValue));
			}
		}

		userGroupService.create(userGroup);
		sessionStatus.setComplete();

		return new ModelAndView("redirect:/usergroup/detail.htm?id=" + userGroup.getId());
	}

	@RequestMapping("/usergroup/delete.json")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam("id") Long id) {

		Map<String, Object> result = new HashMap<String, Object>();

		UserGroup userGroup = userGroupService.findById(id);
		if (userGroup != null) {
			userGroupService.delete(userGroup);
			result.put("isSuccess", true);
			result.put("resultMsg", String.format("Delete UserGroup [ID:%d, Name:%s]", id, userGroup.getName()));

		} else {
			result.put("isSuccess", false);
			result.put("resultMsg", String.format("Not Exist UserGroup [ID:%d]", id));
		}

		return result;
	}

	@RequestMapping("/usergroup/detail.htm")
	public ModelAndView detail(@RequestParam("id") Long id) throws Exception {
		ModelAndView mav = new ModelAndView("usergroup/detail");
		mav.addObject("userGroup", userGroupService.findById(id));
		return mav;
	}

	@RequestMapping("/usergroup/search.htm")
	public ModelAndView search(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rowPerPage", defaultValue = "10") int rowPerPage,
			@RequestParam(value = "name", required = false) String name) {

		PaginatedList userGroups = null;

		UserGroup userGroup = new UserGroup();
		if (StringUtils.isNotEmpty(name)) {
			userGroup.setName(name);
		}

		List<UserGroup> list = userGroupService.find(userGroup, new Pageable(page, rowPerPage));
		userGroups = new PaginatedListImpl(list, page, userGroupService.count(userGroup), rowPerPage);

		ModelAndView mav = new ModelAndView("usergroup/search");
		mav.addObject("userGroups", userGroups);
		return mav;
	}

	@RequestMapping(value = "/usergroup/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam("id") Long id) throws Exception {
		UserGroup userGroup = userGroupService.findById(id);

		List<Role> roles = roleService.find();
		for (Role role : roles) {
			if (!userGroup.hasRole(role.getId())) {
				userGroup.addAuthority(new GroupAuth(role, "N"));
			}
		}

		ModelAndView mav = new ModelAndView("usergroup/update");
		mav.addObject("userGroup", userGroup);
		return mav;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/usergroup/update.htm", method = RequestMethod.POST)
	public ModelAndView update(
			@ModelAttribute UserGroup userGroup,
			HttpServletRequest request,
			SessionStatus sessionStatus) {

		logger.debug("name : " + userGroup.getName());

		userGroup.setUpdateTime(new Date());

		userGroup.clearAuthorities();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameterName = (String)parameterNames.nextElement();
			String parameterValue = request.getParameter(parameterName);
			if (StringUtils.isNumeric(parameterName)) {
				Long roleId = Long.valueOf(parameterName);
				userGroup.addAuthority(new GroupAuth(roleId, parameterValue));
			}
		}

		userGroupService.update(userGroup);
		sessionStatus.setComplete();

		ModelAndView mav = new ModelAndView("redirect:/usergroup/detail.htm?id=" + userGroup.getId());
		return mav;
	}
}
