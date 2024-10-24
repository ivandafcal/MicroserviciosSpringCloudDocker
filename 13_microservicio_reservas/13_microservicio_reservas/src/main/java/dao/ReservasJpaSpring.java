package dao;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Reserva;

public interface ReservasJpaSpring  extends JpaRepository<Reserva,Integer>{
	
}
