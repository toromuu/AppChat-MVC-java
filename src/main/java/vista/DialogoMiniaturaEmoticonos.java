package vista;

import java.awt.BorderLayout;
import java.awt.Color;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.ControladorAppChat;
import modelo.Mensaje;
import tds.BubbleText;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

/*
 * Esta clase despliguea una pequena ventana con los diferentes emoticonos de la clase bubbleText que el usuario puede enviar.
 * El emoticono sera enviado al contacto actualmente seleccionado.
 */
public class DialogoMiniaturaEmoticonos extends JDialog {


	private static final String RECURSOS_VISTA_100X100_PNG = "/recursosVista/100x100.png";
	private static final long serialVersionUID = 3781600864626874062L;
	private static int EmojiValue = -1;
	private final JPanel contentPanel = new JPanel();

	public static void main() {
		try {
			DialogoMiniaturaEmoticonos dialog = new DialogoMiniaturaEmoticonos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DialogoMiniaturaEmoticonos() {
		this.setLocation(ControladorVista.frmMain.getLocation());
		
		setMinimumSize(new Dimension(400, 370));
		setIconImage(Toolkit.getDefaultToolkit().getImage(DialogoMiniaturaEmoticonos.class.getResource(RECURSOS_VISTA_100X100_PNG)));
		setBounds(100, 100, 400, 370);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{20, 0, 0, 0, 0, 0, 20, 0};
		gbl_contentPanel.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 20, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JButton Emoji1 = new JButton();
			GridBagConstraints gbc_Emoji1 = new GridBagConstraints();
			gbc_Emoji1.insets = new Insets(0, 0, 5, 5);
			gbc_Emoji1.gridx = 1;
			gbc_Emoji1.gridy = 1;
			contentPanel.add(Emoji1, gbc_Emoji1);
			Emoji1.setIcon((BubbleText.getEmoji(1)));
			Emoji1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					EmojiValue=1;
					enviarEmoticono(EmojiValue);
				}
			});
			
		}
		{
			JButton Emoji2 = new JButton();
			GridBagConstraints gbc_Emoji2 = new GridBagConstraints();
			gbc_Emoji2.insets = new Insets(0, 0, 5, 5);
			gbc_Emoji2.gridx = 3;
			gbc_Emoji2.gridy = 1;
			contentPanel.add(Emoji2, gbc_Emoji2);
			Emoji2.setIcon((BubbleText.getEmoji(2)));
			Emoji2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					EmojiValue=2;
					enviarEmoticono(EmojiValue);
				}
			});
		}
		{
			JButton Emoji3 = new JButton();
			GridBagConstraints gbc_Emoji3 = new GridBagConstraints();
			gbc_Emoji3.insets = new Insets(0, 0, 5, 5);
			gbc_Emoji3.gridx = 5;
			gbc_Emoji3.gridy = 1;
			contentPanel.add(Emoji3, gbc_Emoji3);
			Emoji3.setIcon((BubbleText.getEmoji(3)));
			Emoji3.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					EmojiValue=3;
					enviarEmoticono(EmojiValue);
				}
			});
		}
		{
			JButton Emoji4 = new JButton();
			GridBagConstraints gbc_Emoji4 = new GridBagConstraints();
			gbc_Emoji4.insets = new Insets(0, 0, 5, 5);
			gbc_Emoji4.gridx = 1;
			gbc_Emoji4.gridy = 3;
			contentPanel.add(Emoji4, gbc_Emoji4);
			Emoji4.setIcon((BubbleText.getEmoji(4)));
			Emoji4.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					EmojiValue=4;
					enviarEmoticono(EmojiValue);
				}
			});
		}
		{
			JButton Emoji5 = new JButton();
			GridBagConstraints gbc_Emoji5 = new GridBagConstraints();
			gbc_Emoji5.insets = new Insets(0, 0, 5, 5);
			gbc_Emoji5.gridx = 3;
			gbc_Emoji5.gridy = 3;
			contentPanel.add(Emoji5, gbc_Emoji5);
			Emoji5.setIcon((BubbleText.getEmoji(5)));
			Emoji5.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					EmojiValue=5;
					enviarEmoticono(EmojiValue);
				}
			});
		}
		{
			JButton Emoji6 = new JButton();
			GridBagConstraints gbc_Emoji6 = new GridBagConstraints();
			gbc_Emoji6.insets = new Insets(0, 0, 5, 5);
			gbc_Emoji6.gridx = 5;
			gbc_Emoji6.gridy = 3;
			contentPanel.add(Emoji6, gbc_Emoji6);
			Emoji6.setIcon((BubbleText.getEmoji(6)));
			Emoji6.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					EmojiValue=6;
					enviarEmoticono(EmojiValue);
				}
			});
		}
		{
			JButton Emoji7 = new JButton();
			GridBagConstraints gbc_Emoji7 = new GridBagConstraints();
			gbc_Emoji7.insets = new Insets(0, 0, 5, 5);
			gbc_Emoji7.gridx = 1;
			gbc_Emoji7.gridy = 5;
			contentPanel.add(Emoji7, gbc_Emoji7);
			Emoji7.setIcon((BubbleText.getEmoji(7)));
			Emoji7.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					EmojiValue=7;
					enviarEmoticono(EmojiValue);
				}
			});
		}
		{
			JButton Emoji8 = new JButton();
			GridBagConstraints gbc_Emoji8 = new GridBagConstraints();
			gbc_Emoji8.insets = new Insets(0, 0, 5, 5);
			gbc_Emoji8.gridx = 3;
			gbc_Emoji8.gridy = 5;
			contentPanel.add(Emoji8, gbc_Emoji8);
			Emoji8.setIcon((BubbleText.getEmoji(8)));
			Emoji8.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					EmojiValue=8;
					enviarEmoticono(EmojiValue);
				}
			});
		}
		{
			JButton Emoji9 = new JButton();
			GridBagConstraints gbc_Emoji9 = new GridBagConstraints();
			gbc_Emoji9.insets = new Insets(0, 0, 5, 5);
			gbc_Emoji9.gridx = 5;
			gbc_Emoji9.gridy = 5;
			contentPanel.add(Emoji9, gbc_Emoji9);
			Emoji9.setIcon((BubbleText.getEmoji(9)));
			Emoji9.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					EmojiValue=9;
					enviarEmoticono(EmojiValue);
				}

				
			});
		}
	}
	
	private void enviarEmoticono(int EmojiValue) {
		Mensaje m = ControladorAppChat.getUnicaInstancia().enviarMensaje("",EmojiValue, ControladorVista.getPanelChat().getContactoActual());
		BubbleText mssg = new BubbleText(ControladorVista.getPanelChat().conversacion, m.getEmoticono(), Color.WHITE,	
		ControladorAppChat.getUnicaInstancia().getUsuarioActual().getNombre(), BubbleText.SENT, 10);	
		ControladorVista.getPanelChat().listaBurbujas.add(mssg);
		ControladorVista.getPanelChat().conversacion.add(mssg);
		ControladorVista.getPanelChat().conversacion.repaint();
		ControladorVista.dialogoMiniaturaEmoticonos.setVisible(false);
	}
	
}
