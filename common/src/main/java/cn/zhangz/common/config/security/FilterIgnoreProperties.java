package cn.zhangz.common.config.security;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@RefreshScope
@ConditionalOnExpression("!'${sys.security.ignore}'.isEmpty()")
@ConfigurationProperties(prefix = "sys.security.ignore")
public class FilterIgnoreProperties {
    private List<String> urls = new ArrayList<>();
}