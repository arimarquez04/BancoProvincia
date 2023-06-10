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

public class CuentaDAO {

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

	public void realizarExtraccion(Cuenta cuenta, int importeExtraccion) {
		modificarSaldoParaExtraccion(cuenta, importeExtraccion);
		guardarModificacionDeExtraccionEnTablaMovimientos(cuenta, importeExtraccion, true);
		cuenta.extraer(importeExtraccion);
	}

	public void realizarDeposito(Cuenta cuenta, int importeDeposito) {
		modificarSaldoParaDeposito(cuenta, importeDeposito);
		guardarModificacionDeDepositoEnTablaMovimientos(cuenta, importeDeposito, false);
		cuenta.depositar(importeDeposito);
	}

	public boolean modificarSaldoParaExtraccion(Cuenta cuenta, int importeExtraccion) {
		int filasAfectadas = 0;
		Connection c = null;
		try {
			c = conectar();
			String sql = "UPDATE `bancoprovincia`.`cuenta` SET `saldo_cuenta` = ? WHERE `numero_Cuenta` = ?;";
			PreparedStatement pStmt = c.prepareStatement(sql);
			pStmt.setInt(1, cuenta.getSaldo() - importeExtraccion);
			pStmt.setInt(2, cuenta.getNumeroDeCuenta());
			filasAfectadas = pStmt.executeUpdate();
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
		return filasAfectadas != 0;
	}

	public boolean guardarModificacionDeExtraccionEnTablaMovimientos(Cuenta cuenta, int importeExtraccion,
			boolean esExtraccion) {
		int filasAfectadas = 0;
		Connection c = null;
		try {
			c = conectar();
			String sql = "INSERT INTO `bancoprovincia`.`movimientos` (`esExtraccion`,`numero_Cuenta`,`monto_historial`,`saldo`,`fecha`) VALUES (?,?,?,?,curdate());";
			PreparedStatement pStmt = c.prepareStatement(sql);
			pStmt.setBoolean(1, esExtraccion);
			pStmt.setInt(2, cuenta.getNumeroDeCuenta());
			pStmt.setInt(3, (0 - importeExtraccion));
			pStmt.setInt(4, cuenta.getSaldo() - importeExtraccion);
			filasAfectadas = pStmt.executeUpdate();
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
		return filasAfectadas != 0;
	}

	public boolean modificarSaldoParaDeposito(Cuenta cuenta, int importeDeposito) {
		int filasAfectadas = 0;
		Connection c = null;
		try {
			c = conectar();
			String sql = "UPDATE `bancoprovincia`.`cuenta` SET `saldo_cuenta` = ? WHERE `numero_Cuenta` = ?;";
			PreparedStatement pStmt = c.prepareStatement(sql);
			pStmt.setInt(1, cuenta.getSaldo() + importeDeposito);
			pStmt.setInt(2, cuenta.getNumeroDeCuenta());
			filasAfectadas = pStmt.executeUpdate();
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
		return filasAfectadas != 0;
	}

	public boolean guardarModificacionDeDepositoEnTablaMovimientos(Cuenta cuenta, int importeDeposito,
			boolean esExtraccion) {
		int filasAfectadas = 0;
		Connection c = null;
		try {
			c = conectar();
			String sql = "INSERT INTO `bancoprovincia`.`movimientos` (`esExtraccion`,`numero_Cuenta`,`monto_historial`,`saldo`,`fecha`) VALUES (?,?,?,?,curdate());";
			PreparedStatement pStmt = c.prepareStatement(sql);
			pStmt.setBoolean(1, esExtraccion);
			pStmt.setInt(2, cuenta.getNumeroDeCuenta());
			pStmt.setInt(3, (0 + importeDeposito));
			pStmt.setInt(4, cuenta.getSaldo() + importeDeposito);
			filasAfectadas = pStmt.executeUpdate();
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
		return filasAfectadas != 0;
	}

	public int obtenerNumeroDeExtraccionesMensualesDeUnaCuentaDeAhorro(Cuenta cuenta) {
		int cantidad = 0;
		CuentaAhorroDAO cAhorroDao = new CuentaAhorroDAO();
		cantidad = cAhorroDao.contarCantidadDeExtraccionesMensualesDeUnaCuentaDeAhorro(cuenta);

		return cantidad;
	}
	public int obtenerElMontoDeExtraccionesDiariaDeUnaCuentaDeAhorro(Cuenta cuenta) {
		int cantidad = 0;
		CuentaAhorroDAO cAhorroDao = new CuentaAhorroDAO();
		cantidad = cAhorroDao.contarElMontoDeLasExtraccionesDelDia(cuenta);
		return cantidad;
	}
	public int obtenerElMontoDeExtraccionesDiariaDeUnaCuentaCorriente(Cuenta cuenta) {
		int cantidad = 0;
		CuentaCorrienteDAO cCorrienteDao = new CuentaCorrienteDAO();
		cantidad = cCorrienteDao.contarElMontoDeLasExtraccionesDelDia(cuenta);
		return cantidad;
	}

	public boolean esCorriente(int numeroDeCuenta) {
		boolean valor = false;
		Connection c = null;
		try {
			c = conectar();
			String sql = "SELECT `cuenta`.`esCorriente` FROM `bancoprovincia`.`cuenta` WHERE `cuenta`.`numero_Cuenta` = ?;";
			PreparedStatement pStmt = c.prepareStatement(sql);
			pStmt.setInt(1, numeroDeCuenta);
			ResultSet rs= pStmt.executeQuery();
			while(rs.next()) {
				int tipoObtenido = rs.getInt(1);
				System.out.println(tipoObtenido);
				if (tipoObtenido==1) {
					valor = true;
				}
				else {
					valor = false;
				}
			}
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
		return valor;
	}

	public boolean verificarContrasenaDeUnaCuenta(Cuenta cuenta, int contrasenaIngresada) {
		boolean valor = false;
		Connection c = null;
		try {
			c = conectar();
			String sql = "SELECT `cuenta`.`contrasena_cuenta` FROM `bancoprovincia`.`cuenta` WHERE `cuenta`.`numero_Cuenta` = ?;";
			PreparedStatement pStmt = c.prepareStatement(sql);
			pStmt.setInt(1, cuenta.getNumeroDeCuenta());
			ResultSet rs= pStmt.executeQuery();
			while(rs.next()) {
				int contrasenaObtenida = rs.getInt(1);
				System.out.println(contrasenaObtenida);
				if (contrasenaIngresada==contrasenaObtenida) {
					valor = true;
				}
			}
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
		return valor;
	}
}
