package service;

import java.util.List;

import model.Contacto;

public interface AgendaService {

	boolean agregarContacto(Contacto contacto);

	List<Contacto> recuperarContactos();

	void actualizarContacto(Contacto contacto);

	boolean eliminarContacto(int idContacto);

	Contacto buscarContacto(int idContacto);
}
