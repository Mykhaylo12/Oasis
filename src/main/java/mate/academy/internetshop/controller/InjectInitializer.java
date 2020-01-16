package mate.academy.internetshop.controller;

import mate.academy.internetshop.lib.Injector;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InjectInitializer implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(InjectInitializer.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            LOGGER.info("Dependency injection started");
            Injector.injectDependency();
        } catch (IllegalAccessException e) {
            LOGGER.error("Problem with initialization of dependencies", e);
            throw new RuntimeException();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
