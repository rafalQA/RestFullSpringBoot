package com.utility;

import freemarker.template.TemplateException;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rpiotrowicz on 2017-02-15.
 */
public class FreeMarkerHelper {

    public static String getMergedTemplateWithUserRoot(Object object, String template) {

        Map<String, Object> root = getRoot(object);

        String mergedTemplate = null;

        try {
            mergedTemplate =
                    FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfigurer().getConfiguration()
                            .getTemplate(template), root);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        return mergedTemplate;
    }

    private static FreeMarkerConfigurer freeMarkerConfigurer() throws IOException, TemplateException {
        FreeMarkerConfigurer factoryBean = new FreeMarkerConfigurer();
        factoryBean.setPreferFileSystemAccess(false);
        factoryBean.setTemplateLoaderPath("/templates");
        factoryBean.afterPropertiesSet();

        return factoryBean;
    }

    private static Map<String, Object> getRoot(Object object) {

        String className = object.getClass().getSimpleName();

        String firstCharLowerCaseClassName = String.valueOf(className.charAt(0)).toLowerCase() +
                className.substring(1);

        Map<String, Object> root = new HashMap<>();
        root.put(firstCharLowerCaseClassName, object);

        return root;
    }
}
