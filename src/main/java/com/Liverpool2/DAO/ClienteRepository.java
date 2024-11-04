package com.Liverpool2.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.Liverpool2.DTO.Cliente;
import com.Liverpool2.EXCEPCION.ExcepcionNotFoundPersonal;

@Repository
public class ClienteRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void crearCliente(Cliente cliente) {
		String sql = "INSERT INTO CLIENT (NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, CORREO, DIRECCION_ENVIO) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, cliente.getNombre(), cliente.getApellidoPaterno(), cliente.getApellidoMaterno(), cliente.getCorreo(), cliente.getDireccionEnvio());
	}

	public List<Cliente> obtenerClientes() {
		String sql = "SELECT * FROM CLIENT";
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			Cliente c = new Cliente();
			c.setId(rs.getLong("ID"));
			c.setNombre(rs.getString("NOMBRE"));
			c.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
			c.setApellidoMaterno(rs.getString("APELLIDO_MATERNO"));
			c.setCorreo(rs.getString("CORREO"));
			c.setDireccionEnvio(rs.getString("DIRECCION_ENVIO"));
			return c;
		});
	}

	public void actualizarCliente(Cliente cliente) {
		String sql = "UPDATE CLIENT SET NOMBRE = ?, APELLIDO_PATERNO = ?, APELLIDO_MATERNO = ?, CORREO = ?, DIRECCION_ENVIO = ? WHERE ID = ?";
		//Ejecuta la actualizacion y guarda el numero de filas afectadas
		  int rowsAffected = jdbcTemplate.update(sql, cliente.getNombre(),
				  cliente.getApellidoPaterno(),
				  cliente.getApellidoMaterno(), cliente.getCorreo(),
				  cliente.getDireccionEnvio(), cliente.getId());
		  //si no se afectaron filas, lanza una excepcion indicando q el cliente no fue encontado
	        if (rowsAffected == 0) {
	            throw new ExcepcionNotFoundPersonal("Cliente con ID " + cliente.getId() + " no encontrado para actualización");
	        }
	    
	}

	public void eliminarCliente(Long id) {
		String sql = "DELETE FROM CLIENT WHERE ID = ?";
		  try {
			  	//ejecuta la eliminacion y guarda el numero de filas afectadas
	            int rowsAffected = jdbcTemplate.update(sql, id);
	            //si no afectaron filas, lanza una excepcion indicando que el cliente no fue encontrado
	            if (rowsAffected == 0) {
	                throw new ExcepcionNotFoundPersonal("Cliente con ID " + id + " no encontrado para eliminación");
	            }
	            //captura la excepcion si no se encuentra ningun cliente y lanza una excepcion personalizada
	        } catch (EmptyResultDataAccessException e) {
	            throw new ExcepcionNotFoundPersonal("Cliente con ID " + id + " no encontrado");
	        }
	    
	}
}
