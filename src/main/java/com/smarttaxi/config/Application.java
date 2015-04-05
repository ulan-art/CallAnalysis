package com.smarttaxi.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Iwan on 21.03.2015
 */

public class Application {

    private static ApplicationContext context;

    static {
        context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static <T> T getBean(Class<T> type) {
        return context.getBean(type);
    }
}
