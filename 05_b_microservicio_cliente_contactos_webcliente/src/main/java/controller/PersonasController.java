package controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import model.Persona;

@RestController
public class PersonasController {

	String urlBase="http://localhost:8080";
	
	@Autowired
	WebClient webClient;
	
	//creacion de una persona y devuelve la lista de personas que ya tenemos mas esa persona
	@GetMapping(value="/personas/{nombre}/{email}/{edad}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Persona> altaNueva(@PathVariable("nombre") String nombre,
						@PathVariable("email") String email, 
						@PathVariable("edad") int edad){
		Persona persona = new Persona(nombre,email,edad);
		
		//llamada con webCliente con post y get (alta contacto y recuperacion de contactos tras ese alta
		webClient
		.post() //devuelve requestBodyUriSpec
		.uri(urlBase+"/contactos") //devuelve requestBodySpec
		.contentType(MediaType.APPLICATION_JSON) //devuelve requestBodySpec
		.bodyValue(persona) //devuelve requestHeadersSpec
		.retrieve() //devuelve responseSpec)
		.bodyToMono(Void.class) //devuelve un Mono de void (no devuelve nada en concreto.
		.block(); //VOID
		
		Persona[] personas = webClient
		.get() //devuelve requestHeadersUriSpec
		.uri(urlBase+"/contactos") //devuelve requestHeadersSpec
		.retrieve() //devuelve ResponseSpec
		.bodyToMono(Persona[].class) //devuelve un mono tipo persona
		.block(); //ya deuvelve el array de objeto persona
		
		return Arrays.asList(personas);
	}
	
}
