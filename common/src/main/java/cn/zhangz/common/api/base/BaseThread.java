package cn.zhangz.common.api.base;

import cn.zhangz.common.constants.AuthenticationContext;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.connector.Request;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 在接口中用异步的方式调用其他接口,且使用的是FeginClient,继承该类可携带认证token.
 * PS:使用restTampalte的调用无法携带token
 */
public abstract class BaseThread extends Thread {

    private Authentication authentication;
    private String authorization;

    public BaseThread(){
        super();
        init();
    }

    public BaseThread(String name){
        super(name);
        init();
    }

    private void init(){
        this.authentication = AuthenticationContext.getAuthentication();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(null != attributes){
            this.authorization = attributes.getRequest().getHeader("authorization");
        }
    }

    @Override
    public void run(){
        SecurityContextHolder.getContext().setAuthentication(this.authentication);
        // 线程远程调用 需要设置request
        if(null == RequestContextHolder.getRequestAttributes()){
            Request request = new Request(new Connector("cn.zhangz.base.Thread"));
            request.setCoyoteRequest(new org.apache.coyote.Request());
            request.addCookie(new Cookie("authorization",authorization));
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new HttpServletRequestWrapper(request)));
        }
        businessRun();
    }

    public abstract void businessRun();
}
