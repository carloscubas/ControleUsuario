package br.cubas.usercontrol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.profiles.active}")
    private String activeProfile;

	@Autowired
	private UserDetailsService userDetailsService;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
        if (activeProfile.trim().equalsIgnoreCase("test")) {
            http.csrf().disable();
            http.authorizeRequests().antMatchers("/h2-console").permitAll();
            http.headers().frameOptions().disable();
        }

		http
		 .authorizeRequests()
		 .antMatchers(HttpMethod.GET, "/user/registration").permitAll()
		  	.antMatchers(HttpMethod.POST, "/user/registration").permitAll()
		 .antMatchers(HttpMethod.GET, "/user/list").hasRole("BASIC")
		  .antMatchers(HttpMethod.GET, "/user/listadmin").hasRole("ADMIN")
                .antMatchers("/user/form**").permitAll()
		  .and()
		  .formLogin()
				.loginPage("/login")
				.permitAll()
		 .and().logout()
				.logoutSuccessUrl("/")
				.permitAll()
            .and().requestCache();

	}

	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }


}
