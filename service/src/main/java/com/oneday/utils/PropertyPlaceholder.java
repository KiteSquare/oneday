package com.oneday.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/12 17:56
 */
public class PropertyPlaceholder extends PropertyPlaceholderConfigurer {
    private static Map<String,String> propertyMap;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        propertyMap = new HashMap<String, String>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            propertyMap.put(keyStr, value);
        }
    }

    //static method for accessing context properties
    public static String getProperty(String name) {
        return propertyMap.get(name);
    }
    public static String getProperty(String name, String defaultValue) {
        String res = propertyMap.get(name);
        return res == null ? defaultValue: res;
    }
}
