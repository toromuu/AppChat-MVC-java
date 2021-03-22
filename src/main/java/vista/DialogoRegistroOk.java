package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;


import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

/*
 * Esta clase despliguea una pequena ventana indicando que el resgistro del usuario se ha realizado con exito
 * Se podria haber usado un JOptionPane.showMessageDialog(); pero en virtud del aprendizaje se decidio realizarlo de esta forma
 * y a su vez mostrar una pequena imagen
 */
public class DialogoRegistroOk extends JDialog {

	private static final String RECURSOS_VISTA_ICONS8_CHECK_DOCUMENT_64_PNG = "/recursosVista/icons8-check-document-64.png";
	private static final String RECURSOS_VISTA_100X100_PNG = "/recursosVista/100x100.png";
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	public static void main(String[] args) {
		try {
			DialogoRegistroOk dialog = new DialogoRegistroOk();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DialogoRegistroOk() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DialogoRegistroOk.class.getResource(RECURSOS_VISTA_100X100_PNG)));
		setBounds(100, 100, 234, 116);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblRegistroCompletado = new JLabel("Registro Completado");
			lblRegistroCompletado.setIcon(new ImageIcon(DialogoRegistroOk.class.getResource(RECURSOS_VISTA_ICONS8_CHECK_DOCUMENT_64_PNG)));
			lblRegistroCompletado.setFont(new Font("Tahoma", Font.PLAIN, 16));
			contentPanel.add(lblRegistroCompletado);
		}
	}

}
