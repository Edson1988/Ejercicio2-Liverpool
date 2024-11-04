package com.Liverpool2.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Pedido {

	private Long id;
	private String codigoProducto;
	private int cantidad;
	private double precio;
	private Long clienteId;
}
