package gmky.core.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("all")
public class ApplicationContextProvider implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static <T> T getBean(Class<T> beanClass) {
        return applicationContext.getBean(beanClass);
    }

    public static <T> T getBean(String beanName, Class<T> beanClass) {
        return applicationContext.getBean(beanName, beanClass);
    }

    public static String getProperty(String key) {
        Environment env = applicationContext.getEnvironment();
        return env.getProperty(key);
    }

    @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        this.applicationContext = appContext;
    }
}
