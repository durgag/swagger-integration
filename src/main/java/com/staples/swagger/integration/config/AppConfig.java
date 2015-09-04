package com.staples.swagger.integration.config;

import java.util.Arrays;

import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.staples.swagger.integration.dto.Person;
import com.staples.swagger.integration.rest.JaxRsApiApplication;
import com.staples.swagger.integration.rest.PeopleRestService;
import com.staples.swagger.integration.services.PeopleService;
import com.wordnik.swagger.jaxrs.config.BeanConfig;
import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;

@Configuration
public class AppConfig {

	public static final String SERVER_PORT = "server.port";
	public static final String SERVER_HOST = "server.host";
	public static final String CONTEXT_PATH = "context.path";
	
	@Bean( destroyMethod = "shutdown")
	public SpringBus cxf() {
		return new SpringBus();
	}
	
	@Bean
	@DependsOn("cxf")
	public Server jaxRsServer() {
		JAXRSServerFactoryBean factory = RuntimeDelegate.getInstance().createEndpoint(jaxRsApiApplication(), JAXRSServerFactoryBean.class);
		factory.setServiceBeans(Arrays.<Object>asList(peopleRestService(), apiListingResourceJSON()));
		factory.setAddress(factory.getAddress());
		factory.setProviders(Arrays.<Object>asList(jsonProvider(),resourceListingProvider(),apiDeclarationProvider()));
		return factory.create();
	}
	
	@Bean
	@Autowired
	public BeanConfig swaggerConfig(Environment environment) {
		final BeanConfig beanConfig = new BeanConfig();
		
		beanConfig.setVersion("1.0.0");
		beanConfig.setScan(true);
		beanConfig.setResourcePackage(Person.class.getPackage().getName());
		beanConfig.setBasePath(
			String.format( "http://%s:%s/%s%s",
				environment.getProperty( SERVER_HOST ),
				environment.getProperty( SERVER_PORT ),
				environment.getProperty( CONTEXT_PATH ),
				jaxRsServer().getEndpoint().getEndpointInfo().getAddress())
		);
		return beanConfig;
	}
	
	@Bean
	public JaxRsApiApplication jaxRsApiApplication() {
		return new JaxRsApiApplication();
	}
	
	@Bean 
	public PeopleService peopleService() {
		return new PeopleService();
	}
	
	@Bean
	public PeopleRestService peopleRestService() {
		return new PeopleRestService();
	}
	
	@Bean
	public ApiListingResourceJSON apiListingResourceJSON() {
		return new ApiListingResourceJSON();
	}
	
	@Bean
	public ApiDeclarationProvider apiDeclarationProvider() {
		return new ApiDeclarationProvider();
	}
	
	@Bean
	public ResourceListingProvider resourceListingProvider() {
		return new ResourceListingProvider();
	}
	
	@Bean
	public JacksonJsonProvider jsonProvider() {
		return new JacksonJsonProvider();
	}
}
