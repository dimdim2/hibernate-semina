package hibernate.semina.controller;

import hibernate.semina.common.PaginatedListImpl;
import hibernate.semina.generic.Pageable;
import hibernate.semina.model.Function;
import hibernate.semina.model.FunctionType;
import hibernate.semina.model.Role;
import hibernate.semina.service.RoleService;

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
@SessionAttributes("role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/role/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm(HttpSession session) {
		ModelAndView mav = new ModelAndView("role/create");
		mav.addObject("role", new Role());
		return mav;
	}

	@RequestMapping(value = "/role/create.htm", method = RequestMethod.POST)
	public ModelAndView create(@ModelAttribute Role role,
			@RequestParam(value="functionNames", required=false) String[] functionNames,
			@RequestParam(value="functionUrls", required=false) String[] functionUrls,
			@RequestParam(value="functionTypes", required=false) FunctionType[] functionTypes,
			SessionStatus sessionStatus) {

		if(functionNames != null && functionNames.length > 0) {
			for(int i = 0; i < functionNames.length; i++) {
				String name = functionNames[i];
				String url = functionUrls[i];
				FunctionType type = functionTypes[i];
				role.addFunction(new Function(name, url, type));
			}
		}

		role.setCreateTime(new Date());
		roleService.create(role);
		sessionStatus.setComplete();

		return new ModelAndView("redirect:/role/detail.htm?id=" + role.getId());
	}

	@RequestMapping("/role/delete.json")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam("id") Long id) {

		Map<String, Object> result = new HashMap<String, Object>();

		Role role = roleService.findById(id);
		if (role != null) {
			roleService.delete(role);
			result.put("isSuccess", true);
			result.put("resultMsg", String.format("Delete Role [ID:%d, Name:%s]", id, role.getName()));

		} else {
			result.put("isSuccess", false);
			result.put("resultMsg", String.format("Not Exist Role [ID:%d]", id));
		}

		return result;
	}

	@RequestMapping("/role/detail.htm")
	public ModelAndView detail(@RequestParam("id") Long id) throws Exception {
		ModelAndView mav = new ModelAndView("role/detail");
		mav.addObject("role", roleService.findById(id));
		return mav;
	}

	@RequestMapping("/role/search.htm")
	public ModelAndView search(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rowPerPage", defaultValue = "10") int rowPerPage,
			@RequestParam(value = "name", required = false) String name) {

		PaginatedList roles = null;

		Role role = new Role();
		if (StringUtils.isNotEmpty(name)) {
			role.setName(name);
		}

		List<Role> list = roleService.find(role, new Pageable(page, rowPerPage));
		roles = new PaginatedListImpl(list, page, roleService.count(role), rowPerPage);

		ModelAndView mav = new ModelAndView("role/search");
		mav.addObject("roles", roles);
		return mav;
	}

	@RequestMapping(value = "/role/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam("id") Long id) throws Exception {
		ModelAndView mav = new ModelAndView("role/update");
		mav.addObject("role", roleService.findById(id));
		return mav;
	}

	@RequestMapping(value = "/role/update.htm", method = RequestMethod.POST)
	public ModelAndView update(
			@ModelAttribute Role role,
			@RequestParam(value="functionNames", required=false) String[] functionNames,
			@RequestParam(value="functionUrls", required=false) String[] functionUrls,
			@RequestParam(value="functionTypes", required=false) FunctionType[] functionTypes,
			SessionStatus sessionStatus) {

		role.clearFunctions();
		if(functionNames != null && functionNames.length > 0) {
			for(int i = 0; i < functionNames.length; i++) {
				String name = functionNames[i];
				String url = functionUrls[i];
				FunctionType type = functionTypes[i];
				role.addFunction(new Function(name, url, type));
			}
		}

		role.setUpdateTime(new Date());
		roleService.update(role);
		sessionStatus.setComplete();

		ModelAndView mav = new ModelAndView("redirect:/role/detail.htm?id=" + role.getId());
		return mav;
	}
}
