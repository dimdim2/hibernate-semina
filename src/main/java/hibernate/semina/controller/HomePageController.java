package hibernate.semina.controller;

import hibernate.semina.auth.Authentication;
import hibernate.semina.model.Menu;
import hibernate.semina.model.MenuType;
import hibernate.semina.model.User;
import hibernate.semina.model.UserGroup;
import hibernate.semina.service.MenuService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomePageController {
	@Autowired
	private MenuService menuService;

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	@RequestMapping("/home/home.htm")
	public String home() {
		return "home/home";
	}

	@RequestMapping("/home/copyright.htm")
	public String copyright() {
		return "home/copyright";
	}

	@RequestMapping("/home/menu.htm")
	public String left() {
		return "home/menu";
	}

	@RequestMapping("/home/top.htm")
	public ModelAndView top() {
		return new ModelAndView("home/top");
	}

	@RequestMapping("/home/menuRender.json")
	@ResponseBody
	public List<Map<String, Object>> menuRender(@RequestParam("node") Long id, HttpSession session) throws Exception {

		List<Menu> authChildMenus = new ArrayList<Menu>();
		Authentication authentication = (Authentication)session.getAttribute(SessionAttrName.AUTHENTICATION);
		User user = authentication.getUser();
		UserGroup group = authentication.getGroup();

		List<Menu> allChildMenus = menuService.findChildMenus(id);
		for (Menu menu : allChildMenus) {
			if (user.getId().equals(Authentication.SUPER_USER_ID) || menu.isAllowedGroup(group.getId())) {
				authChildMenus.add(menu);
			}
		}

		List<Map<String, Object>> authorizedMenus = new ArrayList<Map<String, Object>>();
		for (Menu menu : authChildMenus) {
			Map<String, Object> jsonObj = new HashMap<String, Object>();

			jsonObj.put("id", Long.toString(menu.getId()));
			jsonObj.put("text", menu.getName());
			jsonObj.put("href", menu.getUrl());

			MenuType menuType = menu.getType();
			if (MenuType.LEAF.equals(menuType)) {
				jsonObj.put("leaf", true);
				jsonObj.put("cls", "leafNode");
//				jsonObj.put("icon", "/images/silk/table.png");
				jsonObj.put("iconCls", "leafIcon");
			} else {
				jsonObj.put("cls", "dirNode");
//				jsonObj.put("icon", "/images/silk/application_side_boxes.png");
				jsonObj.put("iconCls", "dirIcon");
			}

			authorizedMenus.add(jsonObj);
		}

		return authorizedMenus;
	}
}
