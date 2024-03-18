package br.com.jtaskmanager;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import br.com.jtaskmanager.connect.SQLiteConnection;
import br.com.jtaskmanager.view.principal.Principal;

public class JTaskManager {

	public static void main(String[] args) {

		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException
				| UnsupportedLookAndFeelException e) {
		}

		SQLiteConnection.urlBanco = "jdbc:sqlite:banco.db";

		var principal = new Principal();
		principal.setVisible(true);
	}
}
