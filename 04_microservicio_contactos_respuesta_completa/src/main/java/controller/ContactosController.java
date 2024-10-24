package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import model.Contacto;
import service.AgendaService;

@CrossOrigin(origins = "*") //PARA APLICAR EL FRONT QUE UTILIZAMOS DE CONTACTOS, evitar que se bloqueen los JavaScript
@RestController
public class ContactosController {

	@Autowired
	AgendaService service;
	
	@GetMapping(value="contactos",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <List<Contacto>> recuperarContactos(){
		List<Contacto> contactos=service.recuperarContactos();
		HttpHeaders headers = new HttpHeaders();
		headers.add("total", String.valueOf(contactos.size())); //venia como integer, lo cambiamos
		return new ResponseEntity<List<Contacto>>(contactos, headers, HttpStatus.OK);
	}
	
	@GetMapping(value="contactos/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <Contacto> recuperarContactos(@PathVariable("id") int id){
		return new ResponseEntity<Contacto>(service.buscarContacto(id),HttpStatus.OK);
		//AQUI NO HAY HEADERS, es el otro constructor mas simple
	}
	
	@PostMapping(value="contactos",consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> guardarContacto(@RequestBody Contacto contacto) {
		if(service.agregarContacto(contacto)) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
	
	@PutMapping(value="contactos",consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> actualizarContacto(@RequestBody Contacto contacto) {
		service.actualizarContacto(contacto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping(value="contactos/{id}")
	public ResponseEntity<Void> eliminarContacto(@PathVariable("id") int idContacto) {
		service.eliminarContacto(idContacto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
