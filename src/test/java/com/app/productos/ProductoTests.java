package com.app.productos;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;



@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class ProductoTests {

	@Autowired
	private ProductoRepositorio repositorio;
	
	@Test
	@Rollback(false)
	@Order(1)
	public void testGuardarProducto() {
		Producto producto = new Producto("Televisor Samsung HD", 3000);
		Producto productoGuardado = repositorio.save(producto);
		assertNotNull(productoGuardado);
	}
	
	@Test  
	@Order(2)
	public void testBuscarProductoPorNombre() {
		String nombre = "Televisor Samsung HD";
		Producto producto = repositorio.findByNombre(nombre);
		assertThat(producto.getNombre()).isEqualTo(nombre);
	}
	
	@Test  
	@Order(3)
	public void testBuscarProductoPorNombreNoExistente() {
		String nombre = "Iphone 11";
		Producto producto = repositorio.findByNombre(nombre);
		assertNull(producto);
	}
	
	@Test
	@Rollback(false)
	@Order(4)
	public void testActualizarProducto() {
		String nombreProducto = "Tv HD";
		Producto producto = new Producto(nombreProducto, 2000);
		producto.setId(1);
		
		repositorio.save(producto);
		Producto actualizarProducto = repositorio.findByNombre(nombreProducto);
		assertThat(actualizarProducto.getNombre()).isEqualTo(nombreProducto);
	}
	
	@Test
	@Order(5)
	public void testListarProductos() {
		List<Producto> productos = (List<Producto>) repositorio.findAll();
		
		for(Producto producto : productos) {
			System.out.println(producto);
		}
		assertThat(productos).size().isGreaterThan(0);
	}
	
	@Test
	@Rollback(false)
	@Order(6)
	public void testEliminarProducto() {
		Integer id = 4;
		boolean esExistenteAndesDeEliminar = repositorio.findById(id).isPresent();
		repositorio.deleteById(id);
		
		boolean noExisteDespuesDeEliminar = repositorio.findById(id).isPresent();
		
		assertTrue(esExistenteAndesDeEliminar);
		assertFalse(noExisteDespuesDeEliminar);
	}

}
