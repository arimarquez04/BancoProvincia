package BancoProvincia.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import BancoProvincia.modelo.*;

public class CuentaAhorroDAO {

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

	public ArrayList<CuentaAhorro> traerTodosLasCuentasDeAhorro() {
		ArrayList<CuentaAhorro> cuentas = new ArrayList<>();
		String columnaNumeroCuenta = "numero_Cuenta";
		String columnaNumeroCliente = "numero_cliente";

		String columnaSaldo = "saldo_cuenta";
		Connection c = null;
		try {
			c = conectar();
			String sql = "SELECT `cuenta`.`saldo_cuenta`,`cuenta`.`numero_cliente`,`cuenta`.`numero_Cuenta` FROM `bancoprovincia`.`cuenta` where esCorriente=0;";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int numeroCuenta = rs.getInt(columnaNumeroCuenta);
				int numeroCliente = rs.getInt(columnaNumeroCliente);
				int saldo = rs.getInt(columnaSaldo);
				CuentaAhorro cAhorro = new CuentaAhorro(numeroCuenta, saldo, numeroCliente);
				cuentas.add(cAhorro);
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
		return cuentas;
	}

	public ArrayList<CuentaAhorro> traerLasCuentasDeAhorroDeUnClienteConSuNumeroDeCliente(Cliente cliente) {
		System.out.println(cliente.getNumeroDeCliente());
		ArrayList<CuentaAhorro> cuentas = new ArrayList<>();
		ArrayList<CuentaAhorro> todasLasCuentas = traerTodosLasCuentasDeAhorro();
		for (CuentaAhorro cAhorro : todasLasCuentas) {
			if (cAhorro.getNumeroDeCliente() == cliente.getNumeroDeCliente()) {
				cuentas.add(cAhorro);
				System.out.println("cuenta de ahorro del cliente encontrada ");
			}
		}

		return cuentas;
	}

	public CuentaAhorro traerCuentaDeAhorroSegunNumeroDeCuenta(int numeroDeCuenta) {
		ArrayList<CuentaAhorro> cuentas = traerTodosLasCuentasDeAhorro();
		CuentaAhorro cAhorro = null;
		for(CuentaAhorro c : cuentas) {
			if(c.getNumeroDeCuenta()==numeroDeCuenta) {
				cAhorro=c;
			}
		}
		return cAhorro;
	}
	
	public int contarCantidadDeExtraccionesMensualesDeUnaCuentaDeAhorro(Cuenta cuenta) {
		int cantidad = -1;
		// select count(*) from movimientos where fecha <= curdate() AND fecha >=
		// date_sub(curdate(), interval 1 month) and numero_Cuenta = ?;
		Connection c = null;
		try {
			c = conectar();
			String sql = "select count(*) from movimientos where fecha <= curdate() AND fecha >= date_sub(curdate(), interval 1 month) "
					+ "and numero_Cuenta = ?;";

			PreparedStatement pStmt = c.prepareStatement(sql);
			pStmt.setInt(1, cuenta.getNumeroDeCuenta());
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				cantidad = rs.getInt(1);
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
		return cantidad;
	}

	public int contarElMontoDeLasExtraccionesDelDia(Cuenta cuenta) {
		// select count(*) from movimientos where fecha = curdate() and esExtraccion = 0
		// and numero_Cuenta = ?;
		int cantidad = -1;
		Connection c = null;
		try {
			c = conectar();
			String sql = "select sum(monto_historial) from movimientos where fecha = curdate() and esExtraccion = 0 and numero_Cuenta = ?;";
			PreparedStatement pStmt = c.prepareStatement(sql);
			pStmt.setInt(1, cuenta.getNumeroDeCuenta());
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				cantidad = rs.getInt(1);
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
		return cantidad;
	}
}
