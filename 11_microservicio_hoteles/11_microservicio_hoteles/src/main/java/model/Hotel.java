package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the hoteles database table.
 * 
 */
@Entity
@Table(name="hoteles")
@NamedQuery(name="Hotel.findAll", query="SELECT h FROM Hotel h")
public class Hotel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idHotel;

	private int categoria;

	private byte disponible;

	private String nombre;

	private double precio;

	public Hotel() {
	}

	public int getIdHotel() {
		return this.idHotel;
	}

	public void setIdHotel(int idHotel) {
		this.idHotel = idHotel;
	}

	public int getCategoria() {
		return this.categoria;
	}

	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}

	public byte getDisponible() {
		return this.disponible;
	}

	public void setDisponible(byte disponible) {
		this.disponible = disponible;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return this.precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

}