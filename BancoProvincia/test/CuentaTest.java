import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import BancoProvincia.modelo.Cliente;
import BancoProvincia.modelo.CuentaAhorro;

class CuentaTest {

	@Test
	void buscarUnaCuentaDeAhorroDeUnClienteConSuNumeroDeCuenta() {
		Cliente cliente = new Cliente();
		CuentaAhorro cAhorro = new CuentaAhorro();
		cAhorro.setNumeroDeCuenta(111332);
		CuentaAhorro cAhorro2 = new CuentaAhorro();
		cAhorro2.setNumeroDeCuenta(554244);
		CuentaAhorro cAhorro3 = new CuentaAhorro();
		cAhorro3.setNumeroDeCuenta(654654);
		cliente.agregarCuenta(cAhorro);
		cliente.agregarCuenta(cAhorro2);
		cliente.agregarCuenta(cAhorro3);
		assertEquals(cAhorro, cliente.buscarCuenta(111332));
	}

}
