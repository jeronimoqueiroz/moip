package br.com.moip;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import br.com.moip.gateway.config.Constants;
import br.com.moip.gateway.config.DefaultProfileUtil;
import br.com.moip.gateway.config.MoipProperties;


@ComponentScan
@EnableAutoConfiguration(exclude = { MetricFilterAutoConfiguration.class, MetricRepositoryAutoConfiguration.class, LiquibaseAutoConfiguration.class })
@EnableConfigurationProperties({ LiquibaseProperties.class , MoipProperties.class})
@EnableDiscoveryClient
@EnableZuulProxy
public class Main {
	 private static final Logger log = LoggerFactory.getLogger(Main.class);

	    @Inject
	    private Environment env;

	    /**
	     * Initializes configdevice.
	     * <p>
	     * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
	     * <p>
	     */
	    @PostConstruct
	    public void initApplication() {
	        log.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
	        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
	        if (activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(Constants.SPRING_PROFILE_PRODUCTION)) {
	            log.error("You have misconfigured your application! It should not run " +
	                "with both the 'dev' and 'prod' profiles at the same time.");
	        }
	        if (activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(Constants.SPRING_PROFILE_CLOUD)) {
	            log.error("You have misconfigured your application! It should not" +
	                "run with both the 'dev' and 'cloud' profiles at the same time.");
	        }
	    }

	    /**
	     * Main method, used to run the application.
	     *
	     * @param args the command line arguments
	     * @throws UnknownHostException if the local host name could not be resolved into an address
	     */
	    public static void main(String[] args) throws UnknownHostException {
	        SpringApplication app = new SpringApplication(Main.class);
	        DefaultProfileUtil.addDefaultProfile(app);
	        Environment env = app.run(args).getEnvironment();
	        log.info("\n----------------------------------------------------------\n\t" +
	                "Application '{}' is running! Access URLs:\n\t" +
	                "Local: \t\thttp://localhost:{}\n\t" +
	                "External: \thttp://{}:{}\n----------------------------------------------------------",
	            env.getProperty("spring.application.name"),
	            env.getProperty("server.port"),
	            InetAddress.getLocalHost().getHostAddress(),
	            env.getProperty("server.port"));
	        
	       
	        

	    }

}
