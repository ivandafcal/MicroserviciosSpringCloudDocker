package controller;

import java.util.Date;
import java.util.stream.Collectors;
import static util.Constants.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@RestController
public class AuthController {

	AuthenticationManager authManager;

	public AuthController(AuthenticationManager authManager) {
		super();
		this.authManager = authManager;
	}
	
	@PostMapping(value="login",produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> login(@RequestParam("user") String user, @RequestParam("pwd") String pwd){
		
		try {
			Authentication authentication=authManager.authenticate(new UsernamePasswordAuthenticationToken(user, pwd));
			return new ResponseEntity<>(getToken(authentication), HttpStatus.OK);
		}
		catch(AuthenticationException ex) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	private String getToken(Authentication authentication) {
		//en el body del token se incluye el usuario 
		//y los roles a los que pertenece, además
		//de la fecha de caducidad y los datos de la firma
		String token = Jwts.builder()
			.setIssuedAt(new Date()) //fecha creación
			.setSubject(authentication.getName()) //usuario
			.claim("authorities",authentication.getAuthorities().stream() //roles
							.map(GrantedAuthority::getAuthority)
							.collect(Collectors.toList()))
			.setExpiration(new Date(System.currentTimeMillis() + TIEMPO_VIDA)) //fecha caducidad,fecha hoy + constante
			.signWith(Keys.hmacShaKeyFor(CLAVE.getBytes()))//clave y algoritmo para firma (signature)				
			.compact(); //generación del token
		return token;
	}
}
