package com.aries.aries_boot.helper;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * create by aries
 * thymeleaf引擎和解析器
 */
public class ThymeleafAppUtil {

    private static TemplateEngine templateEngine;

    static {
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
        resolver.setTemplateMode("XHTML");
        resolver.setPrefix("/WEB-INF/classes/templates/");
        resolver.setSuffix(".html");
        resolver.setCacheTTLMs(360000L);
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);
    }

    public static TemplateEngine getTemplateEngine() {
        return templateEngine;
    }
}
