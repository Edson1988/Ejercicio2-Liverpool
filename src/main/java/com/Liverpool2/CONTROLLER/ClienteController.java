package com.Liverpool2.CONTROLLER;

import java.net.URI;
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

import com.Liverpool2.DTO.Cliente;
import com.Liverpool2.EXCEPCION.ExcepcionNotFoundPersonal;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;


import com.Liverpool2.DAO.ClienteRepository;




@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

	@Autowired
	private ClienteRepository clienteRepository;

	@Operation(summary = "Crear un nuevo cliente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cliente creado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error inesperado", content = @Content)
	})

	// Método para crear un nuevo cliente
	@PostMapping
	public ResponseEntity<Void> crearCliente(@RequestBody Cliente cliente) {
		try {
			logger.info("Datos recibidos: " + cliente.toString());
			clienteRepository.crearCliente(cliente);
			return ResponseEntity.ok().build(); // Retorna 200 OK
		} catch (ExcepcionNotFoundPersonal e) {
			// Manejo de la excepción personalizada si ocurre un error específico
			logger.error("Error al crear cliente: " + e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage()); // Retorna 404 Not Found
		} catch (RuntimeException e) {
			// Manejo de otras excepciones no comprobadas
			logger.error("Error inesperado al crear cliente: " + e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado"); // Retorna 500 Internal Server Error
		}
	}

	@Operation(summary = "Obtener todos los clientes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de clientes"),
			@ApiResponse(responseCode = "404", description = "Clientes no encontrados", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error inesperado", content = @Content)
	})

	// Método para obtener todos los clientes
	@GetMapping
	public ResponseEntity<List<Cliente>> obtenerClientes() {
		try {
			List<Cliente> clientes = clienteRepository.obtenerClientes();
			return ResponseEntity.ok(clientes); // Retorna 200 OK con la lista de clientes
		} catch (ExcepcionNotFoundPersonal e) {
			logger.error("Error al obtener clientes: " + e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage()); // Retorna 404 Not Found
		} catch (RuntimeException e) {
			logger.error("Error inesperado al obtener clientes: " + e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado"); // Retorna 500 Internal Server Error
		}
	}

	@Operation(summary = "Actualizar un cliente existente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error inesperado", content = @Content)
	})

	// Método para actualizar un cliente existente
	@PutMapping
	public ResponseEntity<Void> actualizarCliente(@RequestBody Cliente cliente) {
		try {
			clienteRepository.actualizarCliente(cliente);
			return ResponseEntity.ok().build(); // Retorna 200 OK
		} catch (ExcepcionNotFoundPersonal e) {
			// Manejo de la excepción personalizada si el cliente no se encuentra
			logger.error("Error al actualizar cliente: " + e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage()); // Retorna 404 Not Found
		} catch (RuntimeException e) {
			// Manejo de otras excepciones no comprobadas
			logger.error("Error inesperado al actualizar cliente: " + e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado"); // Retorna 500 Internal Server Error
		}
	}

	@Operation(summary = "Eliminar un cliente por ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cliente eliminado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error inesperado", content = @Content)
	})

	// Método para eliminar un cliente por ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
		try {
			clienteRepository.eliminarCliente(id);
			return ResponseEntity.ok().build(); // Retorna 200 OK
		} catch (ExcepcionNotFoundPersonal e) {
			// Manejo de la excepción personalizada si el cliente no se encuentra
			logger.error("Error al eliminar cliente: " + e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage()); // Retorna 404 Not Found
		} catch (RuntimeException e) {
			// Manejo de otras excepciones no comprobadas
			logger.error("Error inesperado al eliminar cliente: " + e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado"); // Retorna 500 Internal Server Error
		}
	}
}
