package BancoProvincia.modelo;

public abstract class Cuenta {

	private int numeroDeCuenta;
	private int saldo;
	private int numeroDeCliente;
	private int montoDiarioExtraido;
	
	public Cuenta() {

	}
	public Cuenta(int numeroDeCuenta) {
this.numeroDeCuenta = numeroDeCuenta;
	}
	public Cuenta(int numeroDeCuenta, int saldo) {
		this.numeroDeCuenta = numeroDeCuenta;
		this.saldo = saldo;
	}
	public Cuenta(int numeroDeCuenta, int saldo, int numeroCliente) {
		this.numeroDeCuenta = numeroDeCuenta;
		this.saldo = saldo;
		this.numeroDeCliente = numeroCliente;
	}
	public abstract boolean puedeExtraer(int cantidadExtraccion);

	public abstract boolean alcanzoElLimiteDiarioDeExtraccion();

	public abstract boolean isEsCorriente();

	public abstract void setCantidadExtraccionesMensuales(int cantidad);

	public int consultarSaldo() {
		return getSaldo();
	}

	public void depositar(int deposito) {
		saldo = saldo + deposito;

	}

	public void extraer(int cantidadExtraer) {
		saldo = saldo - cantidadExtraer;
	}

	public int getNumeroDeCuenta() {
		return numeroDeCuenta;
	}

	public void setNumeroDeCuenta(int numeroDeCuenta) {
		this.numeroDeCuenta = numeroDeCuenta;
	}

	public int getSaldo() {
		return saldo;
	}

	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}

	public int getNumeroDeCliente() {
		return numeroDeCliente;
	}

	public void setNumeroDeCliente(int numeroDeCliente) {
		this.numeroDeCliente = numeroDeCliente;
	}
	public int getMontoDiarioExtraido() {
		return montoDiarioExtraido;
	}
	public void setMontoDiarioExtraido(int montoDiarioExtraido) {
		this.montoDiarioExtraido = montoDiarioExtraido;
	}

}
