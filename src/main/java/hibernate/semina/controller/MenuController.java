package hibernate.semina.controller;

import hibernate.semina.model.Menu;
import hibernate.semina.model.MenuType;
import hibernate.semina.model.UserGroup;
import hibernate.semina.service.MenuService;
import hibernate.semina.service.UserGroupService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
@SessionAttributes("menu")
public class MenuController {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private MenuService menuService;

	@Autowired
	private UserGroupService userGroupService;

	@RequestMapping(value = "/menu/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm(@RequestParam("parentId") Long parentId) {
		Menu menu = new Menu();
		menu.setParentId(parentId);

		ModelAndView mav = new ModelAndView("menu/create");
		mav.addObject("groups", userGroupService.find());
		mav.addObject("menu", menu);
		return mav;
	}

	@RequestMapping(value = "/menu/create.htm", method = RequestMethod.POST)
	public ModelAndView create(@ModelAttribute Menu menu,
			@RequestParam(value = "groupIds", required = false) Long[] groupIds,
			SessionStatus sessionStatus) {

		if (groupIds != null) {
			for (Long groupId : groupIds) {
				menu.addAllowGroup(new UserGroup(groupId));
			}
		}

		menu.setCreateTime(new Date());
		menuService.create(menu);
		sessionStatus.setComplete();

		ModelAndView mav = new ModelAndView("menu/manager");
		mav.addObject("reloadNodeId", menu.getParentId());
		mav.addObject("redirectUrl", "/menu/detail.htm?id=" + menu.getId());
		return mav;
	}

	@RequestMapping("/menu/delete.json")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam("id") Long id) {

		Map<String, Object> result = new HashMap<String, Object>();

		Menu menu = menuService.findById(id);
		if (menu != null) {
			menuService.delete(menu);
			result.put("isSuccess", true);
			result.put("resultMsg", String.format("Delete Menu [ID:%d, Name:%s]", id, menu.getName()));

		} else {
			result.put("isSuccess", false);
			result.put("resultMsg", String.format("Not Exist Menu [ID:%d]", id));
		}

		return result;
	}

	@RequestMapping("/menu/detail.htm")
	public ModelAndView detail(@RequestParam("id") Long id) throws Exception {
		logger.debug("check .......................... ID:" + id);
		ModelAndView mav = new ModelAndView("menu/detail");
		mav.addObject("menu", menuService.findById(id));
		return mav;
	}

	@RequestMapping(value = "/menu/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam("id") Long id) throws Exception {
		ModelAndView mav = new ModelAndView("menu/update");
		mav.addObject("menu", menuService.findById(id));
		mav.addObject("groups", userGroupService.find());
		return mav;
	}

	@RequestMapping(value = "/menu/update.htm", method = RequestMethod.POST)
	public ModelAndView update(
			@ModelAttribute Menu menu,
			@RequestParam(value = "groupIds", required = false) Long[] groupIds,
			SessionStatus sessionStatus) {

		menu.clearAllowGroups();
		if (groupIds != null) {
			for (Long groupId : groupIds) {
				menu.addAllowGroup(new UserGroup(groupId));
			}
		}

		menu.setUpdateTime(new Date());
		menuService.update(menu);
		sessionStatus.setComplete();

		ModelAndView mav = new ModelAndView("menu/manager");
		mav.addObject("reloadNodeId", menu.getParentId());
		mav.addObject("redirectUrl", "/menu/detail.htm?id=" + menu.getId());
		return mav;
	}

	@RequestMapping("/menu/treeRender.json")
	@ResponseBody
	public List<Object> treeRender(@RequestParam("node") Long id) throws Exception {

		List<Menu> childMenus = menuService.findChildMenus(id);
		List<Object> treeNodes = new ArrayList<Object>(childMenus.size());
		for (Menu menu : childMenus) {
			Map<String, Object> treeNode = new HashMap<String, Object>();

			treeNode.put("id", Long.toString(menu.getId()));
			treeNode.put("text", menu.getName());
			treeNode.put("url", menu.getUrl());
			MenuType menuType = menu.getType();
			if (MenuType.LEAF.equals(menuType)) {
				treeNode.put("leaf", true);
			}
			treeNodes.add(treeNode);
		}

		return treeNodes;
	}

	@RequestMapping("/menu/move.json")
	@ResponseBody
	public Boolean moveMenu(HttpServletResponse response,
			@RequestParam("id") Integer id,
			@RequestParam("oldParentMenuId") Integer oldParentMenuId,
			@RequestParam("newParentMenuId") Integer newParentMenuId,
			@RequestParam("index") Integer index) throws Exception {

		menuService.moveMenu(id, oldParentMenuId, newParentMenuId, index);
		logger.info(String.format("Move CMS MENU [Id:%d, OldParentId:%d, NewParentId:%d, Index:%d]",
				id, oldParentMenuId, newParentMenuId, index));
		return true;
	}

	@RequestMapping("/menu/mainView.htm")
	public String mainView() throws Exception {
		return "menu/mainView";
	}

	@RequestMapping("/menu/treeView.htm")
	public String treeView() throws Exception {
		return "menu/treeView";
	}

}
