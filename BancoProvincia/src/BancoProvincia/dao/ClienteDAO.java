package BancoProvincia.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import BancoProvincia.modelo.Cliente;

public class ClienteDAO {
	private Connection conectar() {
		String url = "jdbc:mysql://localhost:3306/bancoprovincia";
		String usr = "root";
		String pass = "b4CSLw";
		Connection c = null;
		try {
			c = DriverManager.getConnection(url, usr, pass);
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return c;

	}

	public ArrayList<Cliente> traerTodasLosClientes() {

		ArrayList<Cliente> clientes = new ArrayList<>();
		String columnaDni = "dni_cliente";
		String columnaNombre = "nombre_cliente";
		String columnaTelefono = "telefono_cliente";
		String columnaNumeroCliente = "numero_cliente";
		Connection c = null;
		try {
			c = conectar();
			String sql = "SELECT `cliente`.`dni_cliente`,`cliente`.`nombre_cliente`,`cliente`.`telefono_cliente`,`cliente`.`numero_cliente`FROM `bancoprovincia`.`cliente`;";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int dni = rs.getInt(columnaDni);
				String nombre = rs.getString(columnaNombre);
				int telefono = rs.getInt(columnaTelefono);
				int numeroCliente = rs.getInt(columnaNumeroCliente);
				Cliente cliente = new Cliente(dni, nombre, telefono, numeroCliente);
				clientes.add(cliente);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return clientes;

	}

	public Cliente buscarUnClientePorNumeroDeCliente(int numeroABuscar) {
		Cliente clienteEncontrado = null;
		ArrayList<Cliente> listaDeClientes = traerTodasLosClientes();
		for (Cliente c : listaDeClientes) {
			if (c.getNumeroDeCliente() == numeroABuscar) {
				clienteEncontrado = c;
			}
		}
		return clienteEncontrado;
	}

	public boolean verificarContrasena(int contrasenaIngresada, Cliente cliente) {
		String columnaContrasena = "contrasena_cliente";
		int contrasena = -1;
		Connection c = null;
		try {
			c = conectar();
			String sql = "select `cliente`.`contrasena_cliente` from `bancoprovincia`.`cliente` where `cliente`.`numero_cliente` = ?;";
			PreparedStatement pStmt = c.prepareStatement(sql);
			pStmt.setInt(1, cliente.getNumeroDeCliente());
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				contrasena = rs.getInt(columnaContrasena);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		if (contrasena == contrasenaIngresada) {
			return true;
		} else {
			return false;
		}
	}
}