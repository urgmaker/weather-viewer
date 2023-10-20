package pet.project.listeners;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.thymeleaf.TemplateEngine;
import pet.project.utils.ThymeleafUtil;

@WebListener
public class ThymeleafContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        TemplateEngine templateEngine = ThymeleafUtil.buildTemplateEngine(servletContext);

        servletContext.setAttribute("templateEngine", templateEngine);
    }
}
