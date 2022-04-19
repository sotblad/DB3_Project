package se_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class EmployeeApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		
		// bcrypt online https://bcrypt-generator.com/
		
		// password "123" is externally encrypted to the following
		UserDetails user1 = User.withUsername("pvassil")
			     .password("$2a$12$aGJlMr56Hr4HH8kdxIv3LuQVC2l7z9uI3LUHbY3adhOqXhz00UaU.")
			     .roles("EMPLOYEE")
			     .build();
		auth.inMemoryAuthentication().withUser(user1);
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

	/* ZAS keep it simple - No need for custom login page
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/employees/showForm*").hasAnyRole("MANAGER", "ADMIN")
			.antMatchers("/employees/save*").hasAnyRole("MANAGER", "ADMIN")
			.antMatchers("/employees/delete").hasRole("ADMIN")
			.antMatchers("/employees/**").hasRole("EMPLOYEE")
			.antMatchers("/resources/**").permitAll()
			.and()
			.formLogin()
				.loginPage("/showMyLoginPage")
				.loginProcessingUrl("/authenticateTheUser")
				.permitAll()
			.and()
			.logout().permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/access-denied");
		
	}
	*/
		
}






