package PracticaFinal.UmuChat;

import java.awt.EventQueue;

import javax.swing.UnsupportedLookAndFeelException;

import vista.PanelLogin;


//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;



/*
 * Esta clase permite lanzar la Aplicación
 */

public class App 
{
	public static void main(final String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
	//Object props;
	//HiFiLookAndFeel.setCurrentTheme(props);
	//UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				PanelLogin ventana = new PanelLogin();
				ventana.mostrarVentana();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
}
	
}
