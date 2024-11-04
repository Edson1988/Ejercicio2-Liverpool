package com.Liverpool2.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.Liverpool2.DTO.Pedido;
import com.Liverpool2.EXCEPCION.ExcepcionNotFoundPersonal;

@Repository
public class PedidoRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void crearPedido(Pedido pedido) {
		String sql = "INSERT INTO PEDIDO (CODIGO_PRODUCTO, CANTIDAD, PRECIO, CLIENTE_ID) VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sql, pedido.getCodigoProducto(), pedido.getCantidad(), pedido.getPrecio(), pedido.getClienteId());
	}

	public List<Pedido> obtenerPedidos() {
		String sql = "SELECT * FROM PEDIDO";
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			Pedido p = new Pedido();
			p.setId(rs.getLong("ID"));
			p.setCodigoProducto(rs.getString("CODIGO_PRODUCTO"));
			p.setCantidad(rs.getInt("CANTIDAD"));
			p.setPrecio(rs.getDouble("PRECIO"));
			p.setClienteId(rs.getLong("CLIENTE_ID"));
			return p;
		});
	}

	public void actualizarPedido(Pedido pedido) {
		String sql = "UPDATE PEDIDO SET CODIGO_PRODUCTO = ?, CANTIDAD = ?, PRECIO = ?, CLIENTE_ID = ? WHERE ID = ?";
		   // Ejecuta la actualización y guarda el número de filas afectadas.
        int rowsAffected = jdbcTemplate.update(sql, 
            pedido.getCodigoProducto(), 
            pedido.getCantidad(), 
            pedido.getPrecio(), 
            pedido.getClienteId(), 
            pedido.getId()
        );

        // Si no se afectaron filas, lanza una excepción indicando que el pedido no fue encontrado.
        if (rowsAffected == 0) {
            throw new ExcepcionNotFoundPersonal("Pedido con ID " + pedido.getId() + " no encontrado para actualización");
        }
	}

	public void eliminarPedido(Long id) {
		String sql = "DELETE FROM PEDIDO WHERE ID = ?";
		// Ejecuta la eliminación y guarda el número de filas afectadas.
        int rowsAffected = jdbcTemplate.update(sql, id);

        // Si no se afectaron filas, lanza una excepción indicando que el pedido no fue encontrado.
        if (rowsAffected == 0) {
            throw new ExcepcionNotFoundPersonal("Pedido con ID " + id + " no encontrado para eliminación");
        }
    }

}
