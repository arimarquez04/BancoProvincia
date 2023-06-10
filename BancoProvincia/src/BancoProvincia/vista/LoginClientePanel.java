package BancoProvincia.vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import BancoProvincia.dao.ClienteDAO;
import BancoProvincia.modelo.Cliente;

import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class LoginClientePanel extends JPanel {
	private JTextField text_IngresarNumeroCliente;
	private JTextField text_IngresarContrasenaCliente;
	private JLabel lblIngreseSuContrasea;
	private JLabel lable_MensajeErrorDeLogIn;

	/**
	 * Create the panel.
	 */
	public LoginClientePanel() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("LoginClientePanel");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 5, 450, 14);
		add(lblNewLabel);
		
		text_IngresarNumeroCliente = new JTextField();
		text_IngresarNumeroCliente.setBounds(10, 80, 167, 30);
		add(text_IngresarNumeroCliente);
		text_IngresarNumeroCliente.setColumns(10);
		
		JLabel label_IngresarNumeroDeCliente = new JLabel("Ingrese su numero de cliente");
		label_IngresarNumeroDeCliente.setHorizontalAlignment(SwingConstants.LEFT);
		label_IngresarNumeroDeCliente.setBounds(10, 47, 167, 30);
		add(label_IngresarNumeroDeCliente);
		
		text_IngresarContrasenaCliente = new JTextField();
		text_IngresarContrasenaCliente.setColumns(10);
		text_IngresarContrasenaCliente.setBounds(10, 165, 167, 30);
		add(text_IngresarContrasenaCliente);
		
		lblIngreseSuContrasea = new JLabel("Ingrese su contrase\u00F1a numerica de cliente");
		lblIngreseSuContrasea.setHorizontalAlignment(SwingConstants.LEFT);
		lblIngreseSuContrasea.setBounds(0, 121, 316, 57);
		add(lblIngreseSuContrasea);
		
		JButton btn_LogIn = new JButton("LogIn");
		btn_LogIn.setBounds(383, 125, 89, 23);
		add(btn_LogIn);
		
		btn_LogIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				
				int numeroCliente = Integer.parseInt(text_IngresarNumeroCliente.getText());
				int contrasena = Integer.parseInt(text_IngresarContrasenaCliente.getText());
				ClienteDAO cDao = new ClienteDAO();
				Cliente cliente = cDao.buscarUnClientePorNumeroDeCliente(numeroCliente);
				if(cDao.verificarContrasena(contrasena, cliente)==true) {
					System.out.println("se verifico la contraseña!");
					JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((JComponent) ae.getSource());
					marco.setContentPane(new MostrarCuentasPanel(cliente));
					marco.validate();
				}
				
				
			}
		});
		
		lable_MensajeErrorDeLogIn = new JLabel("New label");
		lable_MensajeErrorDeLogIn.setBounds(404, 173, 46, 14);
		add(lable_MensajeErrorDeLogIn);

		
	}

}
