package cn.zhangz.common.config.aspect;


import cn.zhangz.common.constants.AuthenticationContext;
import cn.zhangz.common.utils.DateUtils;
import cn.zhangz.common.utils.IPUtils;
import cn.zhangz.common.utils.TimeUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class WebLogAspect {

	@Value("spring.application.name")
	private String appName;

	private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

	@Pointcut("execution( * cn.zhangz..controller.*.*(..)) "
			+ "&& !execution( * cn.zhangz..controller.BaseController.*(..)) "
			+ "&& !execution( * cn.zhangz..controller.LogController.*(..)) "
			+ "&& !execution( * cn.zhangz..controller.LogDataProvider.*(..)) "
			+ "&& !execution( * cn.zhangz..controller.FileDataProvider.*(..)) " ) // 两个..代表所有子目录，最后括号里的两个..代表所有参数
	public void logPointCut() {
	}

	@Before("logPointCut()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		// 记录下请求内容
		logger.info("------------------------------用户 : " + AuthenticationContext.getLoginCode()
				+ " 请求开始------------------------------");
		logger.info("请求应用 : " + appName);
		logger.info("请求地址 : " + request.getRequestURL().toString());
		logger.info("请求类型 : " + request.getMethod());
		// 获取真实的ip地址
		logger.info("客户地址 : " + IPUtils.getRemoteAddr(request));
		logger.info(
				"请求方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
		logger.info("请求参数 : " + Arrays.toString(joinPoint.getArgs()));
	}

	@AfterReturning(returning = "result", pointcut = "logPointCut()") // returning的值和doAfterReturning的参数名一致
	public void doAfterReturning(Object result) throws Throwable {
		// 处理完请求，返回内容(返回值太复杂时，打印的是物理存储空间的地址)
		logger.info("返回结果 : " + result);
		logger.info("------------------------------用户 : " + AuthenticationContext.getLoginCode()
				+ " 请求结束------------------------------");
	}

	@Around("logPointCut()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		long startTime = System.currentTimeMillis();

		Throwable ex = null;
		Object result = null;
		try {
			result = pjp.proceed();// ob 为方法的返回值
		} catch (Throwable e) {
			logger.error("异常信息 : " + e.getMessage());

			ex = e;
		}

		long endTime = System.currentTimeMillis(); // 2、结束时间
		long executeTime = endTime - startTime;
		logger.info("耗费时长 : " + TimeUtils.formatDateAgo(executeTime));

		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		// JVM信息
		String jvm = String.format("内存信息 : 结束时间: %s  耗费时长: %s  最大内存: %sm  已分配内存: %sm  已分配内存中剩余空间: %sm  最大可用内存: %sm",
				DateUtils.format(endTime, "hh:mm:ss.SSS"), TimeUtils.formatDateAgo(executeTime),
				Runtime.getRuntime().maxMemory() / 1024 / 1024, Runtime.getRuntime().totalMemory() / 1024 / 1024,
				Runtime.getRuntime().freeMemory() / 1024 / 1024, (Runtime.getRuntime().maxMemory()
						- Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1024 / 1024);

		// 打印JVM信息
		if (logger.isDebugEnabled()) {
			logger.debug(jvm);
		}
		
		// 保存日志
		/*LogUtils.saveLog(BaseUtils.getUserId(), AuthenticationContext.getLoginCode(), jvm, result, request, ex, null,
				null, executeTime,pjp.getSignature().getName());*/

		// 抛出异常
		if (ex != null) {
			throw ex;
		}

		return result;
	}
}
