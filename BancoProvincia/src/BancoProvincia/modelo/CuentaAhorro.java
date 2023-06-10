package BancoProvincia.modelo;

public class CuentaAhorro extends Cuenta {

	private static final int LIMITE_DIARIO_EXTRACCION = 15000;
	private static final int TOPE_EXTRACCIONES_MENSUALES = 10;
	private int cantidadExtraccionesMensuales;

	public CuentaAhorro(int numeroDeCuenta, int saldo, int montoDiarioExtraido, int cantidadExtraccionesMensuales) {
		super(numeroDeCuenta, saldo);
		this.cantidadExtraccionesMensuales = cantidadExtraccionesMensuales;
	}

	public CuentaAhorro(int numeroDeCuenta, int saldo) {
		super(numeroDeCuenta, saldo);
	}

	public CuentaAhorro(int numeroDeCuenta, int saldo, int numeroCliente) {
		super(numeroDeCuenta, saldo, numeroCliente);
	}

	public CuentaAhorro() {
		// TODO Auto-generated constructor stub
	}

	public boolean alcanzoElLimiteDiarioDeExtraccion() {
		return getMontoDiarioExtraido() >= LIMITE_DIARIO_EXTRACCION;
	}

	public boolean alcanzoElTopeDeExtraccionesMensuales() {
		return cantidadExtraccionesMensuales >= TOPE_EXTRACCIONES_MENSUALES;
	}

	public boolean tieneSaldoSuficiente(int cantidadExtraccion) {
		if (((getSaldo() - cantidadExtraccion) >= 0) == true) {
			return true;
		} else {
			return false;
		}
	}

	public boolean puedeExtraer(int cantidadExtraccion) {
		if ((tieneSaldoSuficiente(cantidadExtraccion) == true) && (alcanzoElLimiteDiarioDeExtraccion() == false)
				&& (alcanzoElTopeDeExtraccionesMensuales() == false)) {
			System.out.println("puede extraer");
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isEsCorriente() {
		return false;
	}

	public static int getLimiteDiarioExtraccion() {
		return LIMITE_DIARIO_EXTRACCION;
	}

	public static int getTopeExtraccionesMensuales() {
		return TOPE_EXTRACCIONES_MENSUALES;
	}

	public int getCantidadExtraccionesMensuales() {
		return cantidadExtraccionesMensuales;
	}

	public void setCantidadExtraccionesMensuales(int cantidadExtraccionesMensuales) {
		this.cantidadExtraccionesMensuales = cantidadExtraccionesMensuales;
	}

}
