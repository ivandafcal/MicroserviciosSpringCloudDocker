package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import model.Reserva;
import service.ReservasService;

@CrossOrigin(origins="*")
@RestController
public class ReservasController {
	@Autowired
	ReservasService service;
	
	@PostMapping(value="reserva/{personas}", consumes=MediaType.APPLICATION_JSON_VALUE)
	public void generarReserva(@RequestBody Reserva reserva, @PathVariable("personas") int personas) {
		this.service.realizarReserva(reserva, personas);
	}
	
	@GetMapping(value="reservas", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Reserva> getReservas() {
		return this.service.getReservas();
	}
}
