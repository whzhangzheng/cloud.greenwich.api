package cn.zhangz.common.config.security;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@RefreshScope
@ConditionalOnExpression("!'${sys.security.token-access}'.isEmpty()")
@ConfigurationProperties(prefix = "sys.security.token-access")
public class AccessTokenProperties {
    private String appName;
    private String clientId;
    private String secret;

}
