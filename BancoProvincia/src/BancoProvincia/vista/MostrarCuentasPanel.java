package BancoProvincia.vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import BancoProvincia.dao.CuentaAhorroDAO;
import BancoProvincia.dao.CuentaCorrienteDAO;
import BancoProvincia.modelo.Cliente;
import BancoProvincia.modelo.Cuenta;
import BancoProvincia.modelo.CuentaAhorro;
import BancoProvincia.modelo.CuentaCorriente;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class MostrarCuentasPanel extends JPanel {
	private JScrollPane scrollPane;
	private JTable table;
	private JLabel lblNewLabel;
	private ArrayList<Cuenta> cuentas;

	/**
	 * Create the panel.
	 */

	public MostrarCuentasPanel(Cliente cliente) {
		
		setLayout(null);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 46, 365, 344);
		add(scrollPane);

		table = new JTable();
		DefaultTableModel dataModel = new DefaultTableModel(new Object[][] {

		}, new String[] { "N° Cuenta", "Tipo" });

		table.setModel(dataModel);
		scrollPane.setViewportView(table);
		lblNewLabel = new JLabel("MostrarCuentasPanel");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(42, 11, 327, 14);
		add(lblNewLabel);
		
		JButton btn_VerDetallesCuenta = new JButton("Ver Detalles");
		btn_VerDetallesCuenta.setBounds(437, 101, 136, 23);
		add(btn_VerDetallesCuenta);
		btn_VerDetallesCuenta.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				Cuenta c = obtenerCuentaSeleccionada();
				JFrame marco = 
						(JFrame) SwingUtilities.getWindowAncestor((JComponent) ae.getSource());
				marco.setContentPane(new MostrarDetallesCuenta(c));
				marco.validate();
				
			}
		});

		cargarTablaCuenta(dataModel, cliente);
        validate();
	}

	private void cargarTablaCuenta(DefaultTableModel dataModel, Cliente c) {
		
		dataModel.setRowCount(0);
		CuentaAhorroDAO cAhorroDao = new CuentaAhorroDAO();
		CuentaCorrienteDAO cCorrienteDao = new CuentaCorrienteDAO();
		ArrayList<CuentaAhorro> cuentasAhorro = cAhorroDao.traerLasCuentasDeAhorroDeUnClienteConSuNumeroDeCliente(c);
		
		ArrayList<CuentaCorriente> cuentasCorriente = cCorrienteDao.traerLasCuentasCorrienteDeUnClienteConSuNumeroDeCliente(c);
		cuentas = new ArrayList<>();
		//añadir las cuentas al nuevo arraylist
		for(CuentaAhorro cAho : cuentasAhorro) {
			cuentas.add(cAho);
			System.out.println("cuenta de ahorro añadida al arraylist de la tabla" + + cAho.getNumeroDeCuenta());
		}
		for(CuentaCorriente cCorri : cuentasCorriente) {
			cuentas.add(cCorri);
			System.out.println("cuenta corriente añadida al arraylist de la tabla" + cCorri.getNumeroDeCuenta());
		}
		//enseñar los arraylist en la tabla
		System.out.println("Se ejecuto el metodo de carga de cuentas del cliente!");
		for (Cuenta cuenta : cuentas) {
			if(cuenta.isEsCorriente()==true) {
			Object[] fila = new Object[] { cuenta.getNumeroDeCuenta(), "Corriente" };
			dataModel.addRow(fila);
			}
			else {
				Object[] fila = new Object[] { cuenta.getNumeroDeCuenta(), "Ahorro" };
				dataModel.addRow(fila);	
			}
			System.out.println("cuenta " + cuenta.getNumeroDeCuenta() + " añadida!!");
			
		}
	
	}
	private Cuenta obtenerCuentaSeleccionada() {
		// TODO Mejorar (evitar relacionar el �ndice de la tabla con el �ndice del ArrayList)
		int filaSeleccionada = table.getSelectedRow();
		cuentas.get(filaSeleccionada);
		return cuentas.get(filaSeleccionada);
	}
}

	
