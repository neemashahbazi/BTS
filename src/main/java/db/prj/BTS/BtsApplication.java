package db.prj.BTS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;


import javax.faces.webapp.FacesServlet;

@SpringBootApplication
public class BtsApplication extends SpringBootServletInitializer {

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


