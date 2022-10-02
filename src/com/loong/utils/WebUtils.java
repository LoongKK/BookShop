package com.loong.utils;

import org.apache.commons.beanutils.BeanUtils;
import java.util.Map;

public class WebUtils {
    /**
     * 把 Map 中的值注入到对应的 JavaBean 属性中。并返回新的bean
     * @param properties
     * @param bean
     */
    public static <T> T copyParamToBean(Map properties , T bean ){
        try {
            //把所有请求的参数都注入到 bean 对象中
            //public static void populate(Object bean, Map<String,? extends Object> properties)
            //根据指定的name-value对填充指定bean的属性。
            // 依赖于javabean中的setter
            BeanUtils.populate(bean, properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }
}