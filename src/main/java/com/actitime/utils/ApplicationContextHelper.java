package com.actitime.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHelper implements ApplicationContextAware {

    private static final ApplicationContextHelper INSTANCE = new ApplicationContextHelper();
    private ApplicationContext applicationContext;

    private ApplicationContextHelper() {
    }

    public static ApplicationContext getApplicationContext() {
        return INSTANCE.applicationContext;
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        INSTANCE.applicationContext = applicationContext;
    }
}