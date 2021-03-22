package vista;

import java.awt.BorderLayout;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;


import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;

import javax.swing.border.TitledBorder;


import controlador.ControladorAppChat;


import javax.swing.BoxLayout;
import javax.swing.UIManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Esta clase se encarga de desplegar una ventana para que el usuario pueda convertirse en premium
 * Con este nuevo status pude acceder a la funcionalidad adicional de otras ventanas como generar estadisticas
 * o generar pdf con la información sobre sus contactos
 * Esta ventana tiene en cuenta los descuentos posibles del usuario a la hora de calcular el precio que inicialmente
 * es de 20 euros.
 * El usuario puede volver a cambiar sus status a "no premium" cuando lo desee.
 */
public class DialogoConvertirsePremium extends JDialog {

	private static final String RECURSOS_VISTA_100X100_PNG = "/recursosVista/100x100.png";
	private static final String RECURSOS_VISTA_DIAMOND_PNG = "/recursosVista/diamond.png";
	private static final long serialVersionUID = 7614050731714885082L;
	private final JPanel contentPanel = new JPanel();
	private final static float precio = 20;
	private JLabel lbldescuento;
	private JLabel labelpreciofinal ;
	
	public static void main() {
		try {
			DialogoConvertirsePremium dialog = new DialogoConvertirsePremium();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DialogoConvertirsePremium() {
		setTitle("Servicio Premium");
		setIconImage(Toolkit.getDefaultToolkit().getImage(DialogoConvertirsePremium.class.getResource(RECURSOS_VISTA_100X100_PNG)));
		setResizable(false);
		setBounds(100, 100, 650, 512);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{20, 0, 0, 0, 0, 20, 0};
		gbl_contentPanel.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblServicioPremium = new JLabel("");
			lblServicioPremium.setFont(new Font("Rubik", Font.BOLD, 13));
			lblServicioPremium.setIcon(new ImageIcon(DialogoConvertirsePremium.class.getResource(RECURSOS_VISTA_DIAMOND_PNG)));
			GridBagConstraints gbc_lblServicioPremium = new GridBagConstraints();
			gbc_lblServicioPremium.gridwidth = 2;
			gbc_lblServicioPremium.insets = new Insets(0, 0, 5, 5);
			gbc_lblServicioPremium.gridx = 2;
			gbc_lblServicioPremium.gridy = 1;
			contentPanel.add(lblServicioPremium, gbc_lblServicioPremium);
		}
		{
			JTextPane txtpnPorSolo = new JTextPane();
			txtpnPorSolo.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Informaci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			txtpnPorSolo.setEditable(false);
			txtpnPorSolo.setText("Por solo 20 € euros al  año podrás contribuir al desarrollo de Umu Chat  \r\ny disfrutar de las todas las funcionalidades del servicio Premium:\r\n\t+generación de estadísticas  \r\n\t+exportación de contactos en Pdf\r\n\t+etc\r\n*Esta tarifa puede variar según la aplicación de determinados descuentos ");
			GridBagConstraints gbc_txtpnPorSolo = new GridBagConstraints();
			gbc_txtpnPorSolo.gridheight = 3;
			gbc_txtpnPorSolo.insets = new Insets(0, 0, 5, 5);
			gbc_txtpnPorSolo.fill = GridBagConstraints.BOTH;
			gbc_txtpnPorSolo.gridx = 2;
			gbc_txtpnPorSolo.gridy = 2;
			contentPanel.add(txtpnPorSolo, gbc_txtpnPorSolo);
		}
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Precio Final", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.insets = new Insets(0, 0, 5, 5);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 2;
			gbc_panel.gridy = 6;
			contentPanel.add(panel, gbc_panel);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			{
				JPanel PanelDescuento = new JPanel();
				panel.add(PanelDescuento);
				{
					JLabel lblDescuentoAplicado = new JLabel("Descuento aplicado :");
					PanelDescuento.add(lblDescuentoAplicado);
				}
				{
					lbldescuento = new JLabel("");
					PanelDescuento.add(lbldescuento);
				}
			}
			{
				JPanel panelPrecio = new JPanel();
				panel.add(panelPrecio);
				{
					JLabel lblPrecioAPagar = new JLabel("Precio a pagar :");
					panelPrecio.add(lblPrecioAPagar);
				}
				{
					labelpreciofinal = new JLabel("");
					panelPrecio.add(labelpreciofinal);
				}
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.insets = new Insets(0, 0, 5, 5);
			gbc_panel.fill = GridBagConstraints.HORIZONTAL;
			gbc_panel.gridx = 2;
			gbc_panel.gridy = 9;
			contentPanel.add(panel, gbc_panel);
			{
				JButton btnPremium = new JButton("Convertirse en Premium");
				panel.add(btnPremium);
				btnPremium.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ControladorAppChat.getUnicaInstancia().setPremium(true);
						JOptionPane.showMessageDialog(ControladorVista.frmMain, "Eres Premium.");
						dispose();
					}
				});
			}
			{
				JButton btnCancelarSus = new JButton("Cancelar Suscripción Premium");
				panel.add(btnCancelarSus);
				btnCancelarSus.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						ControladorAppChat.getUnicaInstancia().setPremium(false);
						JOptionPane.showMessageDialog(ControladorVista.frmMain, "Ya no eres Premium");
						dispose();
						
					}
				});
			}
			float descuento = ControladorAppChat.getUnicaInstancia().generarDescuentos(precio);
			lbldescuento.setText(ControladorAppChat.getUnicaInstancia().getUsuarioActual().getDescuento());
			labelpreciofinal.setText(String.valueOf(descuento));
		}
	}

}


