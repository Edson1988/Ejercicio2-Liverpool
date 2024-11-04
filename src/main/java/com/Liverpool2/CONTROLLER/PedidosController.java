package com.Liverpool2.CONTROLLER;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.Liverpool2.DAO.PedidoRepository;
import com.Liverpool2.DTO.Pedido;
import com.Liverpool2.EXCEPCION.ExcepcionNotFoundPersonal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;




@RestController
@RequestMapping("/api/pedidos")
public class PedidosController {

	private static final Logger logger = LoggerFactory.getLogger(PedidosController.class);

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Operation(summary = "Crear un nuevo pedido")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "pedido creado exitosamente"),
			@ApiResponse(responseCode = "404", description = "pedido no encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error inesperado", content = @Content)
	})

	// Método para crear un nuevo pedido
	@PostMapping
	public ResponseEntity<Void> crearPedido(@RequestBody Pedido pedido) {
		try {
			logger.info("Datos recibidos para el pedido: " + pedido.toString());
			pedidoRepository.crearPedido(pedido);
			return ResponseEntity.ok().build(); // Retorna 200 OK
		} catch (ExcepcionNotFoundPersonal e) {
			// Manejo de la excepción personalizada si ocurre un error específico
			logger.error("Error al crear pedido: " + e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage()); // Retorna 404 Not Found
		} catch (RuntimeException e) {
			// Manejo de otras excepciones no comprobadas
			logger.error("Error inesperado al crear pedido: " + e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado"); // Retorna 500 Internal Server Error
		}
	}
	
	@Operation(summary = "Obtener todos los pedidos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de pedidos"),
			@ApiResponse(responseCode = "404", description = "pedidos no encontrados", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error inesperado", content = @Content)
	})

	// Método para obtener todos los pedidos
	@GetMapping
	public ResponseEntity<List<Pedido>> obtenerPedidos() {
		try {
			List<Pedido> pedidos = pedidoRepository.obtenerPedidos();
			return ResponseEntity.ok(pedidos); // Retorna 200 OK con la lista de pedidos
		} catch (ExcepcionNotFoundPersonal e) {
			logger.error("Error al obtener pedidos: " + e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage()); // Retorna 404 Not Found
		} catch (RuntimeException e) {
			logger.error("Error inesperado al obtener pedidos: " + e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado"); // Retorna 500 Internal Server Error
		}
	}
	
	
	@Operation(summary = "Actualizar un pedido existente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Pedido actualizado exitosamente"),
			@ApiResponse(responseCode = "404", description = "pedido no encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error inesperado", content = @Content)
	})

	// Método para actualizar un pedido existente
	@PutMapping
	public ResponseEntity<Void> actualizarPedido(@RequestBody Pedido pedido) {
		try {
			pedidoRepository.actualizarPedido(pedido);
			return ResponseEntity.ok().build(); // Retorna 200 OK
		} catch (ExcepcionNotFoundPersonal e) {
			// Manejo de la excepción personalizada si el pedido no se encuentra
			logger.error("Error al actualizar pedido: " + e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage()); // Retorna 404 Not Found
		} catch (RuntimeException e) {
			// Manejo de otras excepciones no comprobadas
			logger.error("Error inesperado al actualizar pedido: " + e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado"); // Retorna 500 Internal Server Error
		}
	}
	
	@Operation(summary = "Eliminar un pedido por ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "pedido eliminado exitosamente"),
			@ApiResponse(responseCode = "404", description = "pedido no encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error inesperado", content = @Content)
	})

	// Método para eliminar un pedido por ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
		try {
			pedidoRepository.eliminarPedido(id);
			return ResponseEntity.ok().build(); // Retorna 200 OK
		} catch (ExcepcionNotFoundPersonal e) {
			// Manejo de la excepción personalizada si el pedido no se encuentra
			logger.error("Error al eliminar pedido: " + e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage()); // Retorna 404 Not Found
		} catch (RuntimeException e) {
			// Manejo de otras excepciones no comprobadas
			logger.error("Error inesperado al eliminar pedido: " + e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado"); // Retorna 500 Internal Server Error
		}
	}

}
