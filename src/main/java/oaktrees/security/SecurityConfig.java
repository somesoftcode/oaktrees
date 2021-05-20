package oaktrees.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/", "/register", "/register_processing", "/success_registration")
				.permitAll()
			.antMatchers("/onlyForLogined").hasAuthority(Permission.USER_P.getPermission())
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/login").permitAll().usernameParameter("login")
			.defaultSuccessUrl("/onlyForLogined")
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // change to post!
			.invalidateHttpSession(true).clearAuthentication(true)
			.logoutSuccessUrl("/login");
	}
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

//	@Bean
//	@Override
//	protected UserDetailsService userDetailsService() {
//		return new InMemoryUserDetailsManager(
//				User.builder()
//					.username("boss")
//					.password(passwordEncoder().encode("boss5"))
//					.authorities(Role.BOSS.getAuthorities())
//					.build(),
//				User.builder()
//					.username("user")
//					.password(passwordEncoder().encode("user5"))
//					.authorities(Role.USER.getAuthorities())
//					.build()
//				);
//	}
	

	

}
