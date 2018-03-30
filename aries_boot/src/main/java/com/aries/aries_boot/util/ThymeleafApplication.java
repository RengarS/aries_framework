package com.aries.aries_boot.util;

import com.aries.aries_boot.helper.ThymeleafAppUtil;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * create by aries 2018-3-30
 * <p>
 * 解析thymeleaf视图
 */
public class ThymeleafApplication {

    public static void process(HttpServletRequest request, HttpServletResponse response, String result) throws IOException {
        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale());
        Enumeration<String> attributeNames = request.getAttributeNames();
        if (attributeNames.hasMoreElements()) {
            String addName = attributeNames.nextElement();
            context.setVariable(addName, request.getAttribute(addName));
        }


        ThymeleafAppUtil.getTemplateEngine().process(result, context, response.getWriter());
    }
}
