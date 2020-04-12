package net.gaox.search.index;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p> 数据 </p>
 * ApplicationContextAware 需要获取应用上下文
 * PriorityOrdered 初始化优先级
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/20 16:07
 */
@Component
public class DataTable implements ApplicationContextAware, PriorityOrdered {

    private static ApplicationContext applicationContext;

    public static final Map<Class, Object> dataTableMap = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(
            ApplicationContext applicationContext) throws BeansException {
        DataTable.applicationContext = applicationContext;
    }

    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }

    @SuppressWarnings("all")
    public static <T> T of(Class<T> clazz) {

        T instance = (T) dataTableMap.get(clazz);
        if (null != instance) {
            return instance;
        }
        /**
         * 当获取不到bean时，将bean通过class的方式添加到
         */
        dataTableMap.put(clazz, bean(clazz));
        return (T) dataTableMap.get(clazz);
    }

    /**
     * 通过bean的名称加载bean
     *
     * @param beanName bean名称
     * @param <T>      bean范型
     * @return bean
     */
    @SuppressWarnings("all")
    private static <T> T bean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    @SuppressWarnings("all")
    private static <T> T bean(Class clazz) {
        return (T) applicationContext.getBean(clazz);
    }
}
