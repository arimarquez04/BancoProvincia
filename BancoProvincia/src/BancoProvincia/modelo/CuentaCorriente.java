package BancoProvincia.modelo;

public class CuentaCorriente extends Cuenta {

	private static final int LIMITE_DIARIO_EXTRACCION = 50000;
	private static final int TOPE_DE_SOBREGIRO = -5000;
	
	
	public CuentaCorriente(int numeroCuenta, int saldo) {
		super(numeroCuenta, saldo);
	}

	public CuentaCorriente(int numeroCuenta, int saldo, int numeroCliente) {
		super(numeroCuenta, saldo, numeroCliente);
	}

	public CuentaCorriente() {
		// TODO Auto-generated constructor stub
	}

	public CuentaCorriente(int numeroCuenta) {
		super(numeroCuenta); 
	}

	public boolean alcanzoElLimiteDiarioDeExtraccion() {
		if (getMontoDiarioExtraido() >= LIMITE_DIARIO_EXTRACCION) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean tieneSaldoSuficiente(int nuevaExtraccion) {
		if((getSaldo() - nuevaExtraccion) < TOPE_DE_SOBREGIRO) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean puedeExtraer(int nuevaExtraccion) {
		if ((alcanzoElLimiteDiarioDeExtraccion() == false) && (tieneSaldoSuficiente(nuevaExtraccion) == true)) {
			return true;
		} else {
			return false;
		}
	}

	public int consultarTopeDeSobregiro() {
		return getTOPE_DE_SOBREGIRO();
	}
public boolean isEsCorriente() {
	return true;
}
 	public static int getLIMITE_DIARIO_EXTRACCION() {
		return LIMITE_DIARIO_EXTRACCION;
	}

	public static int getTOPE_DE_SOBREGIRO() {
		return TOPE_DE_SOBREGIRO;
	}

	public void setCantidadExtraccionesMensuales(int cantidad) {
	}


}
