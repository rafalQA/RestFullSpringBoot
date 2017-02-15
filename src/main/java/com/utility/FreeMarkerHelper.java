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

        Map<String, Object> root = getUserRoot(object);

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

    private static Map<String, Object> getUserRoot(Object object) {
        Map<String, Object> root = new HashMap<>();
        root.put(object.getClass().getSimpleName().toLowerCase(), object);

        return root;
    }
}
