package inicio;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/*@Bean
	public InMemoryUserDetailsManager usersdetails() throws Exception{
		List<UserDetails> users=List.of(
				User
				.withUsername("user1")
				//.password("contraseñaalfanumericararaenBCrypt")
				.password("{noop}user1") //noop es para poner contraseña no codificada
				.authorities("USERS") //roles
				.build(),
				User
				.withUsername("user2")
				.password("{noop}user2")
				.authorities("OPERATOR")
				.build(),
				User
				.withUsername("admin")
				.password("{noop}admin")
				.authorities("USERS","ADMIN")
				.build()
				);
		return new InMemoryUserDetailsManager(users);
	}*/
	
	@Bean //usar cuando tienes bbdd con usuarios y demas
	public JdbcUserDetailsManager usersDetailsJdbc() {
		
		DriverManagerDataSource ds=new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/agenda2?serverTimezone=UTC");
		ds.setUsername("root");
		ds.setPassword("1234");
		JdbcUserDetailsManager jdbcDetails=new JdbcUserDetailsManager(ds);
		
		jdbcDetails.setUsersByUsernameQuery("select user, pwd, enabled"
           	+ " from springsecurity.users where user=?");
		jdbcDetails.setAuthoritiesByUsernameQuery("select user, rol "
           	+ "from springsecurity.roles where user=?");
		return jdbcDetails;
	}
	
	/*@Bean   //solo si utilizamos BCryp se descomenta
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}*/
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.csrf(cus->cus.disable()) //desactivado el csrf
		.authorizeHttpRequests(aut->aut
			.requestMatchers(HttpMethod.POST,"/contactos").hasAuthority("ADMIN")
			.requestMatchers(HttpMethod.DELETE,"/contactos/**").hasAnyAuthority("ADMIN","OPERATOR")
			.requestMatchers("/contactos").authenticated()
			.anyRequest().permitAll() //para todos los recursos que no sean los 3 anteriores, puede entrar
			)
		.httpBasic(Customizer.withDefaults());
		return http.build();
		
	}
}
