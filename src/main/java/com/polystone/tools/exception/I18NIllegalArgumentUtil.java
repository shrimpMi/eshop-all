package com.polystone.tools.exception;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * 获取message
 * 
 * @author jimmy
 * @version V1.0, 2017年1月9日
 * @see [com.hmt.core.utils.I18NIllegalArgumentUtil]
 * @since [产品/模块版本]
 * @secret {秘密}
 */
public class I18NIllegalArgumentUtil {

    private final String DEFAULTMESSAGE = "unkown err";
    @Autowired
    private ResourceBundleMessageSource messageSource;

    private static I18NIllegalArgumentUtil instance;

    public static I18NIllegalArgumentUtil getInstance() {
        return instance;
    }

    public void init() {
        instance = this;
    }

    /**
     * 获取消息
     * 
     * @param arguments
     * @return [参数说明]
     * @see com.polystone.tools.exception.I18NIllegalArgumentUtil#getMessage
     */
    public String getMessage(Object... arguments) {
        if (arguments == null) {
            return null;
        }
        String[] params = null;
        if (arguments.length > 1) {
            params = new String[arguments.length - 1];
            for (int i = 1; i < arguments.length; i++) {
                params[i - 1] = messageSource.getMessage(arguments[i] + "", null, DEFAULTMESSAGE, Locale.getDefault());
            }
        }
        return messageSource.getMessage(arguments[0] + "", params, arguments[0] + "", Locale.getDefault());
    }

    public void setMessageSource(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

}
