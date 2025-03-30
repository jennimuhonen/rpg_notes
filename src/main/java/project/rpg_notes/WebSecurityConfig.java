package project.rpg_notes;

//!!tän joutui erikseen lisäämään, antMatcher ei itse ymmärtänyt yskää!!
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity(securedEnabled=true)
public class WebSecurityConfig {
	
	private UserDetailsService userDetailsService;
	
	public WebSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService=userDetailsService;
	}
	
	private static final AntPathRequestMatcher[] WHITE_LIST_URLS = {
			new AntPathRequestMatcher("/api/npcs**"),
			new AntPathRequestMatcher("/api/places**"),
			new AntPathRequestMatcher("/h2-console/**"),
	};
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(
				authorize -> authorize
				.requestMatchers(antMatcher("/css/**")).permitAll()
				.requestMatchers(WHITE_LIST_URLS).permitAll()
				.requestMatchers(antMatcher("/signup")).permitAll()
				.requestMatchers(antMatcher("/")).permitAll()
				.requestMatchers(antMatcher("/index")).permitAll()
				.anyRequest().authenticated())
				.headers(headers ->
				headers.frameOptions(frameOptions -> frameOptions
						.disable())) //tällä mahdollistetaan h2consolin toiminta
				.httpBasic(Customizer.withDefaults())  // Mahdollistaa Basic Authin Postmanissa (saatu ChatGPT:ltä)
				.formLogin(formlogin ->
					formlogin.loginPage("/login")
					.defaultSuccessUrl("/", true)
					.permitAll())
				.logout(logout -> logout
						.logoutSuccessUrl("/?logout") //ChatGPT:n neuvosta lisäsin tämän, jotta ulos kirjautuessa päädytään etusivulle ja siellä on huomautus ulos kirjautumisesta.
						.permitAll())
				.csrf(csrf -> csrf.disable()); //tämä vain kehitystä varten, ei tuotantoon!
		
		return http.build();
		
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

}
