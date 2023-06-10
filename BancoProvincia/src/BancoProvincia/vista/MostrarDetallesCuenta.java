package BancoProvincia.vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BancoProvincia.dao.CuentaAhorroDAO;
import BancoProvincia.dao.CuentaCorrienteDAO;
import BancoProvincia.dao.CuentaDAO;
import BancoProvincia.modelo.Cuenta;
import BancoProvincia.modelo.CuentaAhorro;
import BancoProvincia.modelo.CuentaCorriente;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MostrarDetallesCuenta extends JPanel {

	private JScrollPane scrollPane;
	private JTable table;
	private JTextField text_MontoDeOperacion;
	private JLabel lbl_NumeroDeCuenta;
	private JLabel lbl_SaldoCuenta;
	private JLabel lbl_TipoDeCuenta;

	/**
	 * Create the panel.
	 */
	public MostrarDetallesCuenta(Cuenta cuenta) {
		setLayout(null);

		lbl_NumeroDeCuenta = new JLabel();
		lbl_NumeroDeCuenta.setBounds(34, 55, 290, 14);
		lbl_NumeroDeCuenta.setText("N° de Cuenta: " + Integer.toString(cuenta.getNumeroDeCuenta()));
		add(lbl_NumeroDeCuenta);

		lbl_SaldoCuenta = new JLabel();
		lbl_SaldoCuenta.setBounds(34, 91, 290, 14);
		lbl_SaldoCuenta.setText("Saldo de la cuenta : " + Integer.toString(cuenta.getSaldo()));
		add(lbl_SaldoCuenta);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(349, 54, 365, 344);
		add(scrollPane);

		table = new JTable();
		DefaultTableModel dataModel = new DefaultTableModel(new Object[][] {

		}, new String[] { "Operacion", "Importe", "Saldo" });

		table.setModel(dataModel);
		scrollPane.setViewportView(table);

		text_MontoDeOperacion = new JTextField();
		text_MontoDeOperacion.setBounds(142, 169, 86, 20);
		add(text_MontoDeOperacion);
		text_MontoDeOperacion.setColumns(10);

		JLabel lblNewLabel = new JLabel("Ingrese Importe de Operaccion");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(34, 144, 305, 14);
		add(lblNewLabel);

		JButton btn_RealizarDeposito = new JButton("Realizar Deposito");
		btn_RealizarDeposito.setBounds(182, 200, 142, 23);
		add(btn_RealizarDeposito);

		JButton btn_RealizarExtraccion = new JButton("Realizar Extraccion");
		btn_RealizarExtraccion.setBounds(34, 200, 142, 23);
		add(btn_RealizarExtraccion);
		
		lbl_TipoDeCuenta = new JLabel("");
		lbl_TipoDeCuenta.setBounds(30, 24, 294, 14);
		if(cuenta.isEsCorriente()) {
			lbl_TipoDeCuenta.setText("Cuenta Corriente");
		}
		else {
			lbl_TipoDeCuenta.setText("Cuenta de Ahorro");
		}
		add(lbl_TipoDeCuenta);

		btn_RealizarExtraccion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int montoExtraccion = Integer.parseInt(text_MontoDeOperacion.getText());
				if (cuenta.isEsCorriente()==true) {
					// cuenta corriente
					CuentaDAO cDao = new CuentaDAO();
					cuenta.setMontoDiarioExtraido(cDao.obtenerElMontoDeExtraccionesDiariaDeUnaCuentaCorriente(cuenta));
					if(cuenta.puedeExtraer(montoExtraccion)) {
						cDao.realizarExtraccion(cuenta, montoExtraccion);
						/*if ((cuenta.puedeExtraer(montoExtraccion))&&
							((cDao.obtenerElMontoDeExtraccionesDiariaDeUnaCuentaCorriente(cuenta)+montoExtraccion)
									<= new CuentaCorriente().getLIMITE_DIARIO_EXTRACCION())){*/
					}
				} else {
					// cuenta de ahorro					
					CuentaDAO cDao = new CuentaDAO();
					cuenta.setMontoDiarioExtraido(cDao.obtenerElMontoDeExtraccionesDiariaDeUnaCuentaDeAhorro(cuenta));
					cuenta.setCantidadExtraccionesMensuales(cDao.obtenerNumeroDeExtraccionesMensualesDeUnaCuentaDeAhorro(cuenta));
					if(cuenta.puedeExtraer(montoExtraccion)){	
					cDao.realizarExtraccion(cuenta, montoExtraccion);
	
					//esta es la antigua condicion que usaba pero la cambie para optimizar el codigo
					/*if ((cuenta.puedeExtraer(montoExtraccion)) && 
					(cDao.obtenerNumeroDeExtraccionesMensualesDeUnaCuentaDeAhorro(cuenta) < new CuentaAhorro().getTopeExtraccionesMensuales()) 
					&& (cDao.obtenerElMontoDeExtraccionesDiariaDeUnaCuentaDeAhorro(cuenta)< new CuentaAhorro().getLimiteDiarioExtraccion())) { */
						
					}
				}
				lbl_NumeroDeCuenta.setText("N° de Cuenta: " + Integer.toString(cuenta.getNumeroDeCuenta()));
				text_MontoDeOperacion.setText(null);
				lbl_SaldoCuenta.setText("Saldo de la cuenta : " + Integer.toString(cuenta.getSaldo()));
			}
		});
	
		btn_RealizarDeposito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int montoDeposito = Integer.parseInt(text_MontoDeOperacion.getText());
				CuentaDAO cdao = new CuentaDAO();
				cdao.realizarDeposito(cuenta, montoDeposito);
				lbl_NumeroDeCuenta.setText("N° de Cuenta: " + Integer.toString(cuenta.getNumeroDeCuenta()));
				text_MontoDeOperacion.setText(null);
				lbl_SaldoCuenta.setText("Saldo de la cuenta : " + Integer.toString(cuenta.getSaldo()));
				
			}
		});

	}
	
	
}

