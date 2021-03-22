package vista;

import javax.swing.JFrame;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


import javax.swing.JButton;
import java.awt.Component;


import javax.swing.Box;


import java.awt.Dimension;

import javax.swing.border.TitledBorder;


import controlador.ControladorAppChat;
import modelo.Contacto;
import modelo.Grupo;



/*
 * Esta clase define un panel para modificar un grupo
 * Basicamente se mostrará un combobox con los grupos que el usuario tiene derecho de administrador
 * es decir, el usuario solo vera en el combobox los grupos que ha creado y que pueda modificar.
 * Una vez seleccionado el grupo, al presionar el boton registrar se desplegara un panel crear grupo
 * pero a diferencia de la opcion de crear grupo directamente, el jlist con los contactos ananidos apararecera con los contactos
 * del grupo
 */

public class PanelModificarGrupo extends JPanel {


	private static final long serialVersionUID = 1L;
	private JFrame ventana;
	@SuppressWarnings("unused")
	private JPanel jpanelAnterior;
	private Grupo grupoSeleccionado;



	public PanelModificarGrupo(JFrame frame) {

		ventana=frame;
		jpanelAnterior = (JPanel) ventana.getContentPane();
		setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0};
		gbl_panel.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 20, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Grupo a Modificar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.gridheight = 4;
		gbc_panel_2.gridwidth = 12;
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 1;
		panel.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{20, 68, 0, 0, 20, 0};
		gbl_panel_2.rowHeights = new int[]{0, 22, 0, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		List<Grupo> gruposModifi = ControladorAppChat.getUnicaInstancia().obtenerListaGruposAdmin();
		if (gruposModifi.size()>0) {
			grupoSeleccionado = gruposModifi.get(0);
		}
		
		String[] nombreGrupos = new String[gruposModifi.size()];
		int i = 0;
		synchronized (nombreGrupos) {
			for (Contacto contacto : gruposModifi) {
				nombreGrupos[i] = contacto.getNombre();
				i++;
			}
		}	
		
		JComboBox<String> comboBox = new JComboBox<String>(nombreGrupos);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridwidth = 3;
		gbc_comboBox.anchor = GridBagConstraints.NORTH;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		panel_2.add(comboBox, gbc_comboBox);

		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				grupoSeleccionado = gruposModifi.get(comboBox.getSelectedIndex());
			}
		});
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 12;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 5;
		panel.add(panel_1, gbc_panel_1);
		
		JButton button_1 = new JButton("Modificar Grupo");
		panel_1.add(button_1);
		button_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (grupoSeleccionado!=null) {
					PanelCrearGrupo modificarGrupo = new PanelCrearGrupo(frame, grupoSeleccionado);
					ventana.setContentPane(modificarGrupo);
					ventana.revalidate();
				}
				else {
					JOptionPane.showMessageDialog(ControladorVista.frmMain, "Error, grupo seleccionado incorrecto");
				}
			}
		});
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		panel_1.add(rigidArea);
		
		JButton btnCancelar = new JButton("Volver");
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventana.setContentPane(ControladorVista.pnlChat);
				ventana.revalidate();	
			}
		});
		panel_1.add(btnCancelar);

	}

}
