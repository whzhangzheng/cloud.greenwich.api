package cn.zhangz.common.constants;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

/**
 * 获取认证信息
 * 
 * @author vic
 *
 */
public class AuthenticationContext {
	public static Authentication getAuthentication() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		return authentication;
	}

	public static String getLoginCode() {
		Authentication authentication = getAuthentication();
		return null==authentication?"anonymous":authentication.getName();
	}

	public static Collection<? extends GrantedAuthority> getGrantedAuthority() {
		Authentication authentication = getAuthentication();
		return null==authentication?null:authentication.getAuthorities();
	}
}
