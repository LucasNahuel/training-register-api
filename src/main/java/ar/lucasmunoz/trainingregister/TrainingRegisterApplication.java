package ar.lucasmunoz.trainingregister;

import ar.lucasmunoz.trainingregister.Repositories.SimpleCORSFilter;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;


@SpringBootApplication
public class TrainingRegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingRegisterApplication.class, args);
	}


	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter{

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable();

		}



		@Bean
		public CorsConfigurationSource corsConfigurationSource() {
			final CorsConfiguration configuration = new CorsConfiguration();
			configuration.setAllowedOrigins(Collections.unmodifiableList(Arrays.asList("*")));
			configuration.setAllowedMethods(Collections.unmodifiableList(Arrays.asList("HEAD",
					"GET", "POST", "PUT", "DELETE", "PATCH")));
			// setAllowCredentials(true) is important, otherwise:
			// The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
			configuration.setAllowCredentials(true);
			// setAllowedHeaders is important! Without it, OPTIONS preflight request
			// will fail with 403 Invalid CORS request
			configuration.setAllowedHeaders(Collections.unmodifiableList(Arrays.asList("Authorization", "Cache-Control", "Content-Type" )));
			final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			source.registerCorsConfiguration("/**", configuration);
			return source;
		}



	}

	@Configuration
	public class WebConfig extends WebMvcConfigurerAdapter {

		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**")
					.allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
		}
	}


	@Configuration
	@EnableTransactionManagement
	class TransactionsConfig{

		@Bean
		public DataSource dataSource() throws SQLException {


			MysqlDataSource dataSource = new MysqlDataSource();

			dataSource.setUser("root");
			dataSource.setPassword("can_317");
			dataSource.setServerName("localhost");
			dataSource.setPortNumber(3306);
			dataSource.setDatabaseName("training_db");
			dataSource.setServerTimezone("UTC");



			return dataSource; // (1)




		}

		@Bean
		public PlatformTransactionManager transactionManager() throws SQLException {
			return new DataSourceTransactionManager(dataSource()); // (2)
		}


	}








}
