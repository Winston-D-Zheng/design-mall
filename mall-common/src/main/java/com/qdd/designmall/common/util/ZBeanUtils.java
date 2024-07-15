package com.qdd.designmall.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

@UtilityClass
public class ZBeanUtils {
    public static void copyProperties(Object source, Object target) {

        // 过滤空值
        String[] ignoreProperties = Stream.of(BeanUtils.getPropertyDescriptors(source.getClass()))
                .filter(p -> {
                    Method readMethod = p.getReadMethod();
                    if (readMethod == null) return true;
                    try {
                        if (readMethod.invoke(source) == null) return true;
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                    return false;
                }).map(PropertyDescriptor::getName).toArray(String[]::new);

        // 返回结果
        BeanUtils.copyProperties(source, target, ignoreProperties);
    }
}