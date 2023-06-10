package BancoProvincia.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import BancoProvincia.vista.LoginClientePanel;
import BancoProvincia.vista.LoginCuentaPanel;

public class App {
	public static void main(String[] args) {
		JFrame marco = new JFrame();
		marco.setBounds(0, 0, 800, 600);
		marco.setVisible(true);
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar jMb = new JMenuBar();
		marco.setJMenuBar(jMb);

		JMenuItem menuCliente = new JMenuItem("Login Cliente");
		menuCliente.setSelected(true);
		JMenuItem menuCuenta = new JMenuItem("Login Cuenta");
		menuCuenta.setSelected(true);
		jMb.add(menuCliente);
		jMb.add(menuCuenta);
		marco.getContentPane().setLayout(null);
		marco.setVisible(true);

		//Aqui el usuario puede elegir de que manera iniciar sesion
		menuCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new LoginClientePanel());
				marco.validate();
			}
		});

		menuCuenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new LoginCuentaPanel());
				marco.validate();
			}
		});
	}

}
