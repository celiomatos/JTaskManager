package br.com.jtaskmanager.view.tarefa;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

import br.com.jtaskmanager.controller.TarefaController;
import br.com.jtaskmanager.filter.TarefaFilter;
import br.com.jtaskmanager.model.Tarefa;
import br.com.jtaskmanager.util.Utils;
import br.com.jtaskmanager.view.tarefa.form.TarefaForm;

public class TarefaList extends javax.swing.JInternalFrame {

	private TarefaController tarefaController;
	private List<Tarefa> tarefas;
	private TarefaFilter filter;

	public TarefaList() {
		tarefaController = new TarefaController();
		tarefas = new ArrayList<>();
		initComponents();
		setMask();
		setVisible(true);
	}

	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		tabTarefas = new javax.swing.JTable();
		jPanel2 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		txtNome = new javax.swing.JTextField();
		jLabel3 = new javax.swing.JLabel();
		ftxDataInicial = new javax.swing.JFormattedTextField();
		jLabel4 = new javax.swing.JLabel();
		ftxDataFinal = new javax.swing.JFormattedTextField();
		jLabel5 = new javax.swing.JLabel();
		cbxOrder = new javax.swing.JComboBox<>();
		jPanel3 = new javax.swing.JPanel();
		bttPesquisar = new javax.swing.JButton();
		bttNovo = new javax.swing.JButton();
		bttAlterar = new javax.swing.JButton();
		bttExcluir = new javax.swing.JButton();

		setClosable(true);
		setTitle("Tarefas");

		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Tarefas"));

		tabTarefas.setModel(new TableTarefaModel(tarefas));
		jScrollPane1.setViewportView(tabTarefas);

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addComponent(jScrollPane1).addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
						.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
						.addContainerGap()));

		jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisar"));

		jLabel1.setText("Nome:");

		jLabel2.setText("Data criação:");

		jLabel3.setText("de:");

		jLabel4.setText("ate:");

		jLabel5.setText("Ordenar por:");

		cbxOrder.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome", "Data criação" }));

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(txtNome, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE))
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel2Layout.createSequentialGroup().addGap(18, 18, 18)
										.addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(ftxDataInicial)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel4)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(ftxDataFinal))
								.addGroup(jPanel2Layout.createSequentialGroup().addGap(21, 21, 21).addComponent(jLabel2,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel2Layout.createSequentialGroup()
										.addComponent(cbxOrder, 0, javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addContainerGap())
								.addGroup(jPanel2Layout.createSequentialGroup()
										.addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGap(68, 68, 68)))));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel1).addComponent(jLabel2).addComponent(jLabel5))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel3)
								.addComponent(ftxDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel4)
								.addComponent(ftxDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(cbxOrder, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(16, Short.MAX_VALUE)));

		bttPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/jtaskmanager/img/search.png"))); // NOI18N
		bttPesquisar.setText("Pesquisar");
		bttPesquisar.addActionListener(e -> bttPesquisarActionPerformed(e));

		bttNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/jtaskmanager/img/plus.png"))); // NOI18N
		bttNovo.setText("Novo");
		bttNovo.addActionListener(e -> bttNovoActionPerformed(e));

		bttAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/jtaskmanager/img/edit.png"))); // NOI18N
		bttAlterar.setText("Alterar");
		bttAlterar.addActionListener(e -> bttAlterarActionPerformed(e));

		bttExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/jtaskmanager/img/del.png"))); // NOI18N
		bttExcluir.setText("Excluir");
		bttExcluir.addActionListener(e -> bttExcluirActionPerformed(e));

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup().addContainerGap()
						.addComponent(bttPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(bttNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(bttAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(bttExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
								.addComponent(bttExcluir, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(bttAlterar, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(bttNovo, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(bttPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(layout.createSequentialGroup().addGap(6, 6, 6)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup().addGap(10, 10, 10)
						.addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));

		pack();
	}

	private void bttNovoActionPerformed(ActionEvent evt) {
		var tarefaForm = new TarefaForm(null, true, null);
		tarefaForm.setLocationRelativeTo(this);
		tarefaForm.setVisible(true);
		clearTable();
	}

	private void bttPesquisarActionPerformed(ActionEvent evt) {
		try {
			filter = new TarefaFilter();
			filter.setValid(true);

			if (Utils.isNotEmpty(txtNome.getText())) {
				filter.setNome(txtNome.getText());
			}
			var dateInicial = validDate(ftxDataInicial, null);
			filter.setDataCriacaoInicial(dateInicial);
			filter.setDataCriacaoFinal(validDate(ftxDataFinal, dateInicial));
			filter.setOrdenarPor(0 == cbxOrder.getSelectedIndex() ? "nome" : "data_criacao");
			if (filter.isValid()) {
				tarefas = tarefaController.findAll(filter);
				tabTarefas.setModel(new TableTarefaModel(tarefas));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Falha ao buscar tarefas!");
		}

		clear();
	}

	private void clearTable() {
		tarefas.clear();
		tabTarefas.setModel(new TableTarefaModel(tarefas));
	}

	private void clear() {
		txtNome.setText(null);
		ftxDataInicial.setText(null);
		ftxDataFinal.setText(null);
	}

	private Date validDate(JFormattedTextField value, Date dateInicial) {
		var txt = value.getText().replaceAll("[/ ]", "");
		if (0 < txt.length()) {
			if (!Utils.isStrDateValid(value.getText())) {
				filter.setValid(false);
				JOptionPane.showMessageDialog(this, "Informar a data inicial corretamente!");
				value.requestFocus();
			} else {
				try {
					var date = Utils.strToDate(value.getText());
					if (null != dateInicial && date.getTime() < dateInicial.getTime()) {
						filter.setValid(false);
						JOptionPane.showMessageDialog(this, "Data final não pode ser menor que data inicial");
					} else {
						return date;
					}
				} catch (ParseException e) {
					filter.setValid(false);
					JOptionPane.showMessageDialog(this, "Falha ao obter data inicial/final");
				}
			}
		}
		return null;
	}

	private void setMask() {
		try {
			var formatter = new MaskFormatter("##/##/####");
			formatter.install(ftxDataInicial);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		try {
			var formatter = new MaskFormatter("##/##/####");
			formatter.install(ftxDataFinal);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	}

	private void bttExcluirActionPerformed(ActionEvent evt) {
		var index = tabTarefas.getSelectedRow();
		if (index < 0) {
			JOptionPane.showMessageDialog(this, "Selecione uma tarefa na tabela!");
		} else {
			var tarefa = tarefas.get(index);
			if ("concluido".equalsIgnoreCase(tarefa.getStatus())) {
				JOptionPane.showMessageDialog(this, "Tarefa conluida não pode ser excluida!");
			} else {
				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Deseja realmente excluir a tarefa?",
						"Exclusão", JOptionPane.YES_NO_OPTION)) {
					try {
						tarefaController.delete(tarefa.getId());
						tarefas.remove(index);
						tabTarefas.setModel(new TableTarefaModel(tarefas));
					} catch (SQLException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(this, "Falha ao excluir tarefa");
					}
				}
			}
		}
	}

	private void bttAlterarActionPerformed(ActionEvent evt) {
		var index = tabTarefas.getSelectedRow();
		if (index < 0) {
			JOptionPane.showMessageDialog(this, "Selecione uma tarefa na tabela!");
		} else {
			var idTarefa = tarefas.get(index).getId();
			var tarefaForm = new TarefaForm(null, true, idTarefa);
			tarefaForm.setLocationRelativeTo(this);
			tarefaForm.setVisible(true);
			clearTable();
		}
	}

	private javax.swing.JButton bttAlterar;
	private javax.swing.JButton bttExcluir;
	private javax.swing.JButton bttNovo;
	private javax.swing.JButton bttPesquisar;
	private javax.swing.JComboBox<String> cbxOrder;
	private javax.swing.JFormattedTextField ftxDataInicial;
	private javax.swing.JFormattedTextField ftxDataFinal;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTable tabTarefas;
	private javax.swing.JTextField txtNome;
}
