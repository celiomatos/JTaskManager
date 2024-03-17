package br.com.jtaskmanager.view.report;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import br.com.jtaskmanager.controller.CategoriaController;

public class ReportCategoria extends javax.swing.JInternalFrame {

	private CategoriaController controller;

	public ReportCategoria() {
		initComponents();
		controller = new CategoriaController();
		getCategorias();
		setVisible(true);
	}

	private void getCategorias() {
		try {
			var cate = controller.getTreeCategoria();
			treeCategorias.setModel(CategoriaTreeModel.getTree(cate));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Falha ao buscar categorias!");
		}
	}

	private void initComponents() {

		jScrollPane1 = new javax.swing.JScrollPane();
		treeCategorias = new javax.swing.JTree();

		setClosable(true);
		setTitle("Tree categoria");

		jScrollPane1.setViewportView(treeCategorias);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane1).addContainerGap()));

		pack();
	}

	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTree treeCategorias;
}
