package service;

import java.util.List;

import model.Reserva;

public interface ReservasService {
	void realizarReserva(Reserva reserva, int totalPersonas);
	List<Reserva> getReservas();
}
