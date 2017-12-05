package codesquad.util;

import javax.servlet.http.HttpSession;

import codesquad.model.User;

public class HttpSessionUtil {

	public static String SESSION_USER_NAME = "sessionedUser";

	public static User loginSessionUser(HttpSession session) {
		return (User) session.getAttribute(SESSION_USER_NAME);
	}

	public static boolean isLoginSession(HttpSession session) {
		return loginSessionUser(session) != null;
	}

	public static String loginSessionUserId(HttpSession session) {
		if (loginSessionUser(session) == null) {
			return "";
		}
		return loginSessionUser(session).getId();
	}
}
