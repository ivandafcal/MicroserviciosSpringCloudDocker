package controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import model.Persona;

@RestController
public class PersonasController {
	
	@Autowired
	RestTemplate template;
	
	String url="http://localhost:8080";
	
	@GetMapping(value="/personas/{nombre}/{email}/{edad}", produces=MediaType.APPLICATION_JSON_VALUE)
	//PRIMERA DIFERENCIA, AQUI YA ES RESPONSE ENTITY
	public ResponseEntity<List<Persona>> altaPersona(@PathVariable("nombre") String nombre,
			@PathVariable("email") String email, 
			@PathVariable("edad") int edad){
		Persona persona=new Persona(nombre,email,edad);
		
		try {
			//al lanzar la petición post, si la respuesta contiene un código de error
			//diferente a 200, se producirá una excepción
			template.postForLocation(url+"/contactos", persona);
			//si todo ha ido bien, devolvemos como respuesta la lista de persona
			//e indicamos OK como código de estado de respuesta
			Persona[] personas=template.getForObject(url+"/contactos", Persona[].class);
			return new ResponseEntity<List<Persona>>(Arrays.asList(personas),HttpStatus.OK);
		}
		catch(HttpStatusCodeException exception) {
			//si hubo error en la llamada al microservicio, enviamos a nuestro cliente final un mensaje y
			//añadimos con add una cabecera con el mensaje de error, una lista vacía de personas en el cuerpo
			//y el código de estado enviado desde el microservicio
			HttpHeaders headers=new HttpHeaders();
			headers.add("error", exception.getResponseBodyAsString());
			//enviamos lista vacia de personas, los headers con los errores y el status del error
			return new ResponseEntity<List<Persona>>(new ArrayList<Persona>(),headers,exception.getStatusCode());
		}
		
	}
}
