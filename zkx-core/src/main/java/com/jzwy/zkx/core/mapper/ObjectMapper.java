package com.jzwy.zkx.core.mapper;


import org.springframework.beans.*;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 对象映射
 * <p>
 * 关于对象的映射， 本身是可以使用 org.springframework.beans.BeanUtils 或者 org.apache.commons.beanutils.BeanUtils类的
 * copyProperties做的, 但是该方法竟然不支持对象的基类中含有泛型字段和泛型get,set方法的映射,
 * 故取org.springframework.beans.BeanUtils中的copyProperties重写
 */
public class ObjectMapper {


    public static <S, D> D mapTo(S source, Class<D> destinationClazz) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        D destination = destinationClazz.newInstance();
        copyProperties(destination, source);
        return destination;
    }

    public static <S, D> List<D> mapTo(List<S> sourceList, Class<D> destinationClazz) throws InstantiationException, IllegalAccessException, InvocationTargetException {

        if (sourceList == null || sourceList.isEmpty()) {
            return null;
        }

        List<D> destinationList = new ArrayList<>();

        for (S sourceItem : sourceList) {
            D destination = destinationClazz.newInstance();
            copyProperties(destination, sourceItem);
            destinationList.add(destination);
        }

        return destinationList;
    }


    /**
     * Copy the property values of the given source bean into the target bean.
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the properties match. Any bean properties that the
     * source bean exposes but the target bean does not will silently be ignored.
     * <p>This is just a convenience method. For more complex transfer needs,
     * consider using a full BeanWrapper.
     *
     * @param source the source bean
     * @param target the target bean
     * @throws BeansException if the copying failed
     * @see BeanWrapper
     */
    public static void copyProperties(Object source, Object target) throws BeansException {
        copyProperties(source, target, null, (String[]) null);
    }

    /**
     * Copy the property values of the given source bean into the given target bean,
     * only setting properties defined in the given "editable" class (or interface).
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the properties match. Any bean properties that the
     * source bean exposes but the target bean does not will silently be ignored.
     * <p>This is just a convenience method. For more complex transfer needs,
     * consider using a full BeanWrapper.
     *
     * @param source   the source bean
     * @param target   the target bean
     * @param editable the class (or interface) to restrict property setting to
     * @throws BeansException if the copying failed
     * @see BeanWrapper
     */
    public static void copyProperties(Object source, Object target, Class<?> editable) throws BeansException {
        copyProperties(source, target, editable, (String[]) null);
    }

    /**
     * Copy the property values of the given source bean into the given target bean,
     * ignoring the given "ignoreProperties".
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the properties match. Any bean properties that the
     * source bean exposes but the target bean does not will silently be ignored.
     * <p>This is just a convenience method. For more complex transfer needs,
     * consider using a full BeanWrapper.
     *
     * @param source           the source bean
     * @param target           the target bean
     * @param ignoreProperties array of property names to ignore
     * @throws BeansException if the copying failed
     * @see BeanWrapper
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) throws BeansException {
        copyProperties(source, target, null, ignoreProperties);
    }

    /**
     * Copy the property values of the given source bean into the given target bean.
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the properties match. Any bean properties that the
     * source bean exposes but the target bean does not will silently be ignored.
     *
     * @param source           the source bean
     * @param target           the target bean
     * @param editable         the class (or interface) to restrict property setting to
     * @param ignoreProperties array of property names to ignore
     * @throws BeansException if the copying failed
     * @see BeanWrapper
     */
    private static void copyProperties(Object source, Object target, Class<?> editable, String... ignoreProperties)
            throws BeansException {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
                        "] not assignable to Editable class [" + editable.getName() + "]");
            }
            actualEditable = editable;
        }
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], sourcePd.getPropertyType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        } catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }

    /**
     * Retrieve the JavaBeans {@code PropertyDescriptor}s of a given class.
     *
     * @param clazz the Class to retrieve the PropertyDescriptors for
     * @return an array of {@code PropertyDescriptors} for the given class
     * @throws BeansException if PropertyDescriptor look fails
     */
    public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) throws BeansException {
        CachedPropertyDescriptors cachedPropertyDescriptors = CachedPropertyDescriptors.forClass(clazz);
        return cachedPropertyDescriptors.getPropertyDescriptors();
    }

    /**
     * Retrieve the JavaBeans {@code PropertyDescriptors} for the given property.
     *
     * @param clazz        the Class to retrieve the PropertyDescriptor for
     * @param propertyName the name of the property
     * @return the corresponding PropertyDescriptor, or {@code null} if none
     * @throws BeansException if PropertyDescriptor lookup fails
     */
    public static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, String propertyName)
            throws BeansException {
        CachedPropertyDescriptors cachedPropertyDescriptors = CachedPropertyDescriptors.forClass(clazz);
        return cachedPropertyDescriptors.getPropertyDescriptor(propertyName);
    }
}
