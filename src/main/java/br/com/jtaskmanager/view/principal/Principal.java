package br.com.jtaskmanager.view.principal;

import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import br.com.jtaskmanager.view.report.ReportCategoria;
import br.com.jtaskmanager.view.tarefa.TarefaList;

public class Principal extends javax.swing.JFrame {

	private TarefaList tarefaList;
	private ReportCategoria reportCategoria;

	public Principal() {
		initComponents();
		setLocationRelativeTo(null);
	}

	private void initComponents() {

		deskPane = new javax.swing.JDesktopPane();
		jMenuBar1 = new javax.swing.JMenuBar();
		menuCadastro = new javax.swing.JMenu();
		itemTarefas = new javax.swing.JMenuItem();
		menuRelatorio = new javax.swing.JMenu();
		itemTree = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Gerencia de tarefas");

		javax.swing.GroupLayout deskPaneLayout = new javax.swing.GroupLayout(deskPane);
		deskPane.setLayout(deskPaneLayout);
		deskPaneLayout.setHorizontalGroup(deskPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 800, Short.MAX_VALUE));
		deskPaneLayout.setVerticalGroup(deskPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 577, Short.MAX_VALUE));

		menuCadastro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/jtaskmanager/img/plus.png"))); // NOI18N
		menuCadastro.setText("Cadastro");

		itemTarefas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/jtaskmanager/img/edit.png"))); // NOI18N
		itemTarefas.setText("Tarefas");
		itemTarefas.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				itemTarefasActionPerformed(evt);
			}
		});
		menuCadastro.add(itemTarefas);

		jMenuBar1.add(menuCadastro);

		menuRelatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/jtaskmanager/img/edit.png"))); // NOI18N
		menuRelatorio.setText("Relatórios");

		itemTree.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/jtaskmanager/img/search.png"))); // NOI18N
		itemTree.setText("Tree categorias");
		itemTree.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				itemTreeActionPerformed(evt);
			}
		});
		menuRelatorio.add(itemTree);

		jMenuBar1.add(menuRelatorio);

		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(deskPane));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(deskPane));

		pack();
	}

	private void itemTarefasActionPerformed(java.awt.event.ActionEvent evt) {
		if (0 == deskPane.getComponentCount()) {
			tarefaList = new TarefaList();
			deskPane.add(tarefaList);
			try {
				tarefaList.setSelected(true);
				tarefaList.setMaximum(true);
			} catch (PropertyVetoException ex) {
				Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Abra uma janela por vez");
		}
	}

	private void itemTreeActionPerformed(java.awt.event.ActionEvent evt) {
		if (0 == deskPane.getComponentCount()) {
			reportCategoria = new ReportCategoria();
			deskPane.add(reportCategoria);
			try {
				reportCategoria.setSelected(true);
				reportCategoria.setMaximum(true);
			} catch (PropertyVetoException ex) {
				Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Abra uma janela por vez");
		}
	}

	private javax.swing.JDesktopPane deskPane;
	private javax.swing.JMenuItem itemTarefas;
	private javax.swing.JMenuItem itemTree;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JMenu menuCadastro;
	private javax.swing.JMenu menuRelatorio;

}
