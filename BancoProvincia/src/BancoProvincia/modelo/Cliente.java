package BancoProvincia.modelo;

import java.util.ArrayList;

public class Cliente {

	private int dni;
	private String nombre;
	private int numeroDeCliente;
	private int telefono;
	public Cliente() {
		
	}
	public Cliente(int dni, String nombre, int telefono, int numeroDeCliente) {
		this.dni = dni;
		this.nombre = nombre;
		this.numeroDeCliente = numeroDeCliente;
		this.telefono = telefono;
	}
	
	private ArrayList<Cuenta> cuentas = new ArrayList<>();
	public void agregarCuenta(Cuenta cuentaAgregar) {
		cuentas.add(cuentaAgregar);
	}
	
	public Cuenta buscarCuenta(int numeroDeCuentaABuscar){
		Cuenta cuentaEncotrada = null;
		for(Cuenta c : cuentas) {
			if(c.getNumeroDeCuenta()==numeroDeCuentaABuscar) {
				cuentaEncotrada=c;
			}
		}
		return cuentaEncotrada;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNumeroDeCliente() {
		return numeroDeCliente;
	}

	public void setNumeroDeCliente(int numeroDeCliente) {
		this.numeroDeCliente = numeroDeCliente;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public ArrayList<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(ArrayList<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}
	
}
