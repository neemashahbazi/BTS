package db.prj.BTS;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


import javax.faces.webapp.FacesServlet;

@SpringBootApplication
@EnableScheduling
public class BtsApplication extends SpringBootServletInitializer {

	private static final Logger logger = LogManager.getLogger(BtsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BtsApplication.class, args);
	}


	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		FacesServlet servlet = new FacesServlet();
		ServletRegistrationBean servletRegistrationBean =
				new ServletRegistrationBean(servlet, "*.jsf");
		return servletRegistrationBean;
	}
}


