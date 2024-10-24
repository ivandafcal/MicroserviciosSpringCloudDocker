package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cursos.model.Curso;

import jakarta.annotation.PostConstruct;

@RestController
public class CursosController {
	
	private List<Curso> cursos;
	@PostConstruct //quiere decir que se utiliza despues del constructor
	public void init() {
		cursos=new ArrayList<>();
		cursos.add(new Curso("Spring",25,"tarde"));
		cursos.add(new Curso("Spring Boot",20,"tarde"));
		cursos.add(new Curso("Python",30,"tarde"));
		cursos.add(new Curso("Java EE",50,"fin de semana"));
		cursos.add(new Curso("Java básico",30,"mañana"));
	}
	
	//era JSON VALUE originalmente pero he probado con XML para ver como lo devuelve
	@GetMapping(value="cursos",produces=MediaType.APPLICATION_JSON_VALUE) 
	public List<Curso> getCursos(){
		return cursos;
	}

	@GetMapping(value="curso",produces=MediaType.APPLICATION_JSON_VALUE)
	public Curso getCurso() {
		return new Curso("Java",100,"mañana");
	}
	
	//devuelve la lista segun si contiene el nombre que pasamos por parametro en la URL, http://localhost:8080/cursos/Spring
	@GetMapping(value="cursos/{name}",produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Curso> buscarCursos(@PathVariable("name") String nombre){
		List<Curso> aux=new ArrayList<>();
		for(Curso c:cursos) {
			if(c.getNombre().contains(nombre)) {
				aux.add(c);
			}
		}
		return aux;
	}
	
	@DeleteMapping(value="curso/{name}")
	public void eliminarCurso(@PathVariable("name") String nombre) {
		//expresion lambda
		cursos.removeIf(c->c.getNombre().equals(nombre));
	}
	
	@PostMapping(value="curso",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Curso> altaCurso(@RequestBody Curso curso){
		cursos.add(curso);
		return cursos;
	}
	
	@PutMapping(value="curso",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Curso> actualizaCurso(@RequestBody Curso curso){
		//recorres con un for estandar, porque hay que modificar la posicion del curso haciendo que coincida con el nombre
		for(int i=0;i<cursos.size();i++) {
			if(cursos.get(i).getNombre().equals(curso.getNombre())) {
				cursos.set(i,  curso);
			}
		}
		return cursos;
	}
}
