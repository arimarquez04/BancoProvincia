package BancoProvincia.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import BancoProvincia.modelo.Cliente;
import BancoProvincia.modelo.Cuenta;
import BancoProvincia.modelo.CuentaAhorro;
import BancoProvincia.modelo.CuentaCorriente;

public class CuentaCorrienteDAO {

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
	public ArrayList<CuentaCorriente> traerTodosLasCuentasCorriente() {
		ArrayList<CuentaCorriente> cuentas = new ArrayList<>();
		String columnaNumeroCuenta = "numero_Cuenta";
		String columnaNumeroCliente = "numero_cliente";

		String columnaSaldo = "saldo_cuenta";
		Connection c = null;
		try {
			c = conectar();
			String sql = "SELECT `cuenta`.`saldo_cuenta`,`cuenta`.`numero_cliente`,`cuenta`.`numero_Cuenta` FROM `bancoprovincia`.`cuenta` WHERE esCorriente = 1;";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int numeroCuenta = rs.getInt(columnaNumeroCuenta);
				int numeroCliente = rs.getInt(columnaNumeroCliente);
				int saldo = rs.getInt(columnaSaldo);
				CuentaCorriente cCorriente = new CuentaCorriente(numeroCuenta, saldo, numeroCliente);
				cuentas.add(cCorriente);
				System.out.println("se encontro una cuenta corriente");
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

	public ArrayList<CuentaCorriente> traerLasCuentasCorrienteDeUnClienteConSuNumeroDeCliente(Cliente cliente){
		System.out.println(cliente.getNumeroDeCliente());
		ArrayList<CuentaCorriente> cuentas = new ArrayList<>();
		ArrayList<CuentaCorriente> todasLasCuentas = traerTodosLasCuentasCorriente();
		for(CuentaCorriente cCorriente : todasLasCuentas) {
			if(cCorriente.getNumeroDeCliente()==cliente.getNumeroDeCliente()) {
				cuentas.add(cCorriente);
				System.out.println("cuenta corriente "+ cCorriente.getNumeroDeCuenta() + " añadida");
			}
		}
		
		return cuentas;
	}	

	public CuentaCorriente traerCuentaCorrienteSegunNumeroDeCuenta(int numeroDeCuenta) {
		ArrayList<CuentaCorriente> cuentas = traerTodosLasCuentasCorriente();
		CuentaCorriente cCorri = null;
		for(CuentaCorriente c : cuentas) {
			if(c.getNumeroDeCuenta()==numeroDeCuenta) {
				cCorri=c;
			}
		}
		return cCorri;
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
