package BancoProvincia.vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import BancoProvincia.dao.CuentaAhorroDAO;
import BancoProvincia.dao.CuentaCorrienteDAO;
import BancoProvincia.dao.CuentaDAO;
import BancoProvincia.modelo.Cuenta;
import BancoProvincia.modelo.CuentaAhorro;
import BancoProvincia.modelo.CuentaCorriente;

public class LoginCuentaPanel extends JPanel {
	private JTextField text_IngresarNumeroCliente;
	private JTextField text_ContrasenaIngresada;
	private JLabel label_IngresarContrasena;
	private JLabel label_MensajeErrorDeLogIn;

	/**
	 * Create the panel.
	 */
	public LoginCuentaPanel() {
		setLayout(null);

		JLabel lblNewLabel = new JLabel("LoginCuentaPanel");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 5, 450, 14);
		add(lblNewLabel);

		text_IngresarNumeroCliente = new JTextField();
		text_IngresarNumeroCliente.setBounds(10, 88, 266, 30);
		add(text_IngresarNumeroCliente);
		text_IngresarNumeroCliente.setColumns(10);

		JLabel lblIngreseSuNumero = new JLabel("Ingrese su numero de cuenta");
		lblIngreseSuNumero.setHorizontalAlignment(SwingConstants.LEFT);
		lblIngreseSuNumero.setBounds(10, 47, 266, 30);
		add(lblIngreseSuNumero);

		text_ContrasenaIngresada = new JTextField();
		text_ContrasenaIngresada.setColumns(10);
		text_ContrasenaIngresada.setBounds(10, 193, 266, 30);
		add(text_ContrasenaIngresada);

		label_IngresarContrasena = new JLabel("Ingrese su contrase\u00F1a numerica");
		label_IngresarContrasena.setHorizontalAlignment(SwingConstants.LEFT);
		label_IngresarContrasena.setBounds(10, 152, 266, 30);
		add(label_IngresarContrasena);

		JButton btn_LogIn = new JButton("LogIn");
		btn_LogIn.setBounds(413, 128, 89, 23);
		add(btn_LogIn);
		btn_LogIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				int numeroCuenta = Integer.parseInt(text_IngresarNumeroCliente.getText());
				int contrasenaObtenida = Integer.parseInt(text_ContrasenaIngresada.getText());
				CuentaDAO cDao = new CuentaDAO();
				if (text_IngresarNumeroCliente.getText() != null) {
					if (cDao.esCorriente(numeroCuenta)) {
						CuentaCorriente cCorri = new CuentaCorriente();
						CuentaCorrienteDAO cCorriDao = new CuentaCorrienteDAO();
						cCorri = cCorriDao.traerCuentaCorrienteSegunNumeroDeCuenta(numeroCuenta);
						if (cDao.verificarContrasenaDeUnaCuenta(cCorri, contrasenaObtenida)) {
							JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((JComponent) ae.getSource());
							marco.setContentPane(new MostrarDetallesCuenta(cCorri));
							marco.validate();
						}
					} else {
						CuentaAhorro cAhorro = new CuentaAhorro();
						CuentaAhorroDAO cAhorroDao = new CuentaAhorroDAO();
						cAhorro = cAhorroDao.traerCuentaDeAhorroSegunNumeroDeCuenta(contrasenaObtenida);
						if (cDao.verificarContrasenaDeUnaCuenta(cAhorro, contrasenaObtenida)) {
							JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((JComponent) ae.getSource());
							marco.setContentPane(new MostrarDetallesCuenta(cAhorro));
							marco.validate();
						}
					}
				}
			}
		});

		label_MensajeErrorDeLogIn = new JLabel("New label");
		label_MensajeErrorDeLogIn.setBounds(434, 160, 46, 14);
		add(label_MensajeErrorDeLogIn);

	}
}
