package dao;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Hotel;

public interface HotelesJpaSpring  extends JpaRepository<Hotel,Integer>{
	
}
