package hibernate.semina.interceptor;

import hibernate.semina.auth.Authority;
import hibernate.semina.service.FunctionService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AuthenticationInterceptor implements HandlerInterceptor {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private FunctionService functionService;

	private List<String> ignoreUris;

	private AntPathMatcher matcher = new AntPathMatcher();

	public List<String> getIgnoreUris() {
		return ignoreUris;
	}

	public void setIgnoreUris(List<String> ignoreUris) {
		this.ignoreUris = ignoreUris;
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String uri = request.getRequestURI();
		logger.debug(String.format("Requrested URI [%s]", uri));
		/*
		if (ignoreUris != null && !ignoreUris.isEmpty()) {
			for (String excludeUrl : ignoreUris) {
				if (matcher.match(excludeUrl, uri)) {
					logger.debug(format("Ignore authentication [%s]", uri));
					return true;
				}
			}
		}

		HttpSession session = request.getSession(true);
		Authentication authentication = (Authentication)session.getAttribute("authentication");

		if (authentication == null) {
			logger.info("Session out!!!");
			response.sendRedirect("/error/sessionOut.jsp");
			return false;
		}

		Authority authority = null;
		String userId = (String)session.getAttribute("userId");
		if (userId.equals(Authentication.SUPER_USER_ID)) {
			logger.info(format("Request of Super user!!! [URI:%s]", uri));
			authority = new Authority("CRUD");

		} else {
			Function function = functionService.findByUrl(uri);
			if (function == null) {
				logger.info(format("Access none service page!!! [URI:%s]", uri));
				response.sendRedirect("/error/noneServicePage.jsp");
				return false;
			}

			authority = authentication.getAuthority(function.getRole().getId());
			if (!isAuthenticate(function.getAuthType(), authority)) {
				logger.info(format("Not authority request!!! [ID:%s, URI:%s]", userId, uri));
				response.sendRedirect("/error/hasNotAuthority.jsp");
				return false;
			}
		}

		request.setAttribute("authority", authority);
*/
		request.setAttribute("authority", new Authority("CRUD"));
		return true;
	}

	private boolean isAuthenticate(String cmsFunctionType, Authority authority) {
		if (cmsFunctionType.equalsIgnoreCase("ANY")) {
			return true;
		} else if (cmsFunctionType.equalsIgnoreCase("CREATE")) {
			return authority.isCreate();
		} else if (cmsFunctionType.equalsIgnoreCase("READ")) {
			return authority.isRead();
		} else if (cmsFunctionType.equalsIgnoreCase("UPDATE")) {
			return authority.isUpdate();
		} else if (cmsFunctionType.equalsIgnoreCase("DELETE")) {
			return authority.isDelete();
		}

		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object model, ModelAndView mav)
			throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object model,
			Exception exception) throws Exception {

	}

}
