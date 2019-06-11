package cn.zhangz.common.api.base;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseController {
    @Value("spring.application.name")
    private String appName;

    @ModelAttribute
    public void init(Model model) {
        model.addAttribute("appName", this.getPath());
    }

    public String getPath() {
        return StringUtils.isEmpty(appName) ? "" : "/" + this.appName;
    }
}
