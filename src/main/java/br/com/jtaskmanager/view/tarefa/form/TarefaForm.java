package br.com.jtaskmanager.view.tarefa.form;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.swingbinding.SwingBindings;

import br.com.jtaskmanager.controller.CategoriaController;
import br.com.jtaskmanager.controller.TarefaController;
import br.com.jtaskmanager.model.Categoria;
import br.com.jtaskmanager.model.CriterioAceitacao;
import br.com.jtaskmanager.model.Tarefa;
import br.com.jtaskmanager.util.Utils;

public class TarefaForm extends javax.swing.JDialog {

	private TarefaController tarefaController;
	private CategoriaController categoriaController;
	private TarefaFormModel model;
	private List<CriterioAceitacao> criterios;
	private List<CriterioAceitacao> criteriosAExcluir;
	private List<Categoria> categorias;
	private Tarefa tarefa;

	public TarefaForm(Frame parent, boolean modal, Integer idTarefa) {
		super(parent, modal);
		tarefaController = new TarefaController();
		categoriaController = new CategoriaController();
		model = new TarefaFormModel();
		criterios = new ArrayList<>();
		criteriosAExcluir = new ArrayList<>();
		initComponents();
		initBindings();
		getCategorias();
		getTarefa(idTarefa);
	}

	private void getTarefa(Integer idTarefa) {
		if (null == idTarefa) {
			tarefa = new Tarefa();
		} else {
			try {
				tarefa = tarefaController.findById(idTarefa);
				model.setStrNome(tarefa.getNome());
				cbxCategoria.setSelectedIndex(getIndexCategoria(tarefa.getCategoria().getNome()));
				model.setStrDateCriacao(Utils.dateToStr(tarefa.getDataCriacao()));
				model.setStrDescricaoTarefa(tarefa.getDescricao());
				criterios = tarefa.getCriteriosAceitacao();
				tabCriterios.setModel(new TableCriterioAceitacao(criterios));
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Falha ao buscar tarefa");
			}
		}
	}

	private int getIndexCategoria(String value) {
		int index = 0;
		for (int i = 0; i < categorias.size(); i++) {
			if (value.equalsIgnoreCase(categorias.get(i).getNome())) {
				index = i;
				break;
			}
		}
		return index;
	}

	private void getCategorias() {
		try {
			categorias = categoriaController.findAll();
			model.setStrCategoria(categorias.stream().map(Categoria::getNome).toList());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Falha ao listar categorias");
		}
	}

	public TarefaFormModel getModel() {
		return model;
	}

	public void setModel(TarefaFormModel model) {
		this.model = model;
	}

	private void txtNomeKeyPressed(KeyEvent evt) {
		if (txtNome.getText().length() >= 20) {
			evt.consume();
		}
	}

	private void txaDescricaoTarefaKeyPressed(KeyEvent evt) {
		if (txaDescricaoTarefa.getText().length() >= 100) {
			evt.consume();
		}
	}

	private void txaDescricaoCriterioKeyPressed(KeyEvent evt) {
		if (txaDescricaoCriterio.getText().length() >= 100) {
			evt.consume();
		}
	}

	private void bttExcluirActionPerformed(ActionEvent evt) {
		var index = tabCriterios.getSelectedRow();
		if (index >= 0) {
			if (null != criterios.get(index).getId()) {
				criteriosAExcluir.add(criterios.get(index));
			}
			criterios.remove(index);
			tabCriterios.setModel(new TableCriterioAceitacao(criterios));
		} else {
			JOptionPane.showMessageDialog(this, "Selecione um critério de aceitação");
		}
	}

	private void bttCancelarActionPerformed(ActionEvent evt) {
		this.dispose();
	}

	private void bttSalvarActionPerformed(ActionEvent evt) {
		if (isFormValid() && JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Deseja salvar a tarefa?",
				"Confirmação", JOptionPane.YES_NO_OPTION)) {

			tarefa.setNome(model.getStrNome().trim());
			tarefa.setDescricao(model.getStrDescricaoTarefa().trim());
			var index = cbxCategoria.getSelectedIndex();
			tarefa.setIdCategoria(categorias.get(index).getId());
			tarefa.setCriteriosAceitacao(criterios);

			try {
				tarefa.setDataCriacao(Utils.strToDate(model.getStrDateCriacao()));
				if (null == tarefa.getId()) {
					tarefaController.save(tarefa);
					JOptionPane.showMessageDialog(this, "Tarefa cadastrada com sucesso!");
				} else {
					tarefaController.update(tarefa, criteriosAExcluir);
					JOptionPane.showMessageDialog(this, "Tarefa atualizada com sucesso!");
				}
				this.dispose();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this, "Falha ao cadastrar tarefa!");
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(this, "Informar a data de criação da tarefa corretamente!");
			}
		}
	}

	private void bttIncluirActionPerformed(ActionEvent evt) {
		if (Utils.isNotEmpty(model.getStrDescricaoCriterio())) {
			CriterioAceitacao criterioAceitacao = new CriterioAceitacao();
			criterioAceitacao.setConcluido(false);
			criterioAceitacao.setDescricao(model.getStrDescricaoCriterio());
			criterios.add(criterioAceitacao);
			tabCriterios.setModel(new TableCriterioAceitacao(criterios));
			model.setStrDescricaoCriterio(null);
			txaDescricaoCriterio.requestFocus();
		} else {
			JOptionPane.showMessageDialog(this, "Informe o critério de aceitação");
			txaDescricaoCriterio.requestFocus();
		}
	}

	private boolean isFormValid() {
		var valid = true;
		if (Utils.isEmpty(model.getStrNome())) {
			valid = false;
			JOptionPane.showMessageDialog(this, "Informar o nome da tarefa é obrigatório!");
			txtNome.requestFocus();
		} else if (!Utils.isStrDateValid(model.getStrDateCriacao())) {
			valid = false;
			JOptionPane.showMessageDialog(this, "Informar a data de criação da tarefa corretamente!");
			ftxDataCriacao.requestFocus();
		} else if (Utils.isEmpty(model.getStrDescricaoTarefa())) {
			valid = false;
			JOptionPane.showMessageDialog(this, "Informar a descrição da tarefa é obrigatório!");
			txaDescricaoTarefa.requestFocus();
		} else if (criterios.isEmpty()) {
			valid = false;
			JOptionPane.showMessageDialog(this, "Informar os critério de aceitação da tarefa é obrigatório!");
			txaDescricaoCriterio.requestFocus();
		}

		return valid;
	}

	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		tabCriterios = new javax.swing.JTable();
		jLabel5 = new javax.swing.JLabel();
		jScrollPane3 = new javax.swing.JScrollPane();
		txaDescricaoCriterio = new javax.swing.JTextArea();
		bttIncluir = new javax.swing.JButton();
		bttExcluir = new javax.swing.JButton();
		jPanel2 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		txtNome = new javax.swing.JTextField();
		jLabel2 = new javax.swing.JLabel();
		cbxCategoria = new javax.swing.JComboBox<>();
		jLabel3 = new javax.swing.JLabel();
		ftxDataCriacao = new javax.swing.JFormattedTextField();
		jLabel4 = new javax.swing.JLabel();
		jScrollPane2 = new javax.swing.JScrollPane();
		txaDescricaoTarefa = new javax.swing.JTextArea();
		bttCancelar = new javax.swing.JButton();
		bttSalvar = new javax.swing.JButton();

		try {
			var formatter = new MaskFormatter("##/##/####");
			formatter.install(ftxDataCriacao);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Critérios de aceitação"));

		tabCriterios.setModel(new TableCriterioAceitacao(criterios));
		jScrollPane1.setViewportView(tabCriterios);

		jLabel5.setText("Descrição:");

		txaDescricaoCriterio.setColumns(20);
		txaDescricaoCriterio.setRows(2);
		jScrollPane3.setViewportView(txaDescricaoCriterio);
		txaDescricaoCriterio.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				txaDescricaoCriterioKeyPressed(e);
			}
		});

		bttIncluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/jtaskmanager/img/plus.png"))); // NOI18N
		bttIncluir.setText("Incluir");
		bttIncluir.addActionListener(e -> bttIncluirActionPerformed(e));

		bttExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/jtaskmanager/img/del.png"))); // NOI18N
		bttExcluir.setText("Excluir");
		bttExcluir.addActionListener(e -> bttExcluirActionPerformed(e));

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane3)
						.addGroup(jPanel1Layout.createSequentialGroup()
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jLabel5)
										.addGroup(jPanel1Layout.createSequentialGroup().addComponent(bttIncluir)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(bttExcluir)))
								.addGap(0, 0, Short.MAX_VALUE)))
						.addContainerGap())
				.addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING,
						javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(jLabel5)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 44,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(bttIncluir).addComponent(bttExcluir))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 119,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(54, 54, 54)));

		jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Tarefa"));

		jLabel1.setText("Nome:");

		txtNome.setColumns(20);

		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				txtNomeKeyPressed(e);
			}
		});

		jLabel2.setText("Categoria:");

		jLabel3.setText("Data criação:");

		jLabel4.setText("Descrição:");

		txaDescricaoTarefa.setColumns(20);
		txaDescricaoTarefa.setRows(2);
		jScrollPane2.setViewportView(txaDescricaoTarefa);
		txaDescricaoTarefa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				txaDescricaoTarefaKeyPressed(e);
			}
		});

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel2Layout.createSequentialGroup()
								.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 264,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGroup(jPanel2Layout.createSequentialGroup()
												.addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 170,
														Short.MAX_VALUE)
												.addGap(98, 98, 98)))
								.addGroup(jPanel2Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(cbxCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 172,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 103,
												Short.MAX_VALUE)
										.addComponent(ftxDataCriacao, javax.swing.GroupLayout.PREFERRED_SIZE, 1,
												Short.MAX_VALUE)))
						.addGroup(jPanel2Layout.createSequentialGroup().addComponent(jLabel4).addGap(0, 0,
								Short.MAX_VALUE))
						.addComponent(jScrollPane2)).addContainerGap()));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel1).addComponent(jLabel2).addComponent(jLabel3))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(cbxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(ftxDataCriacao, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel4)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		bttCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/jtaskmanager/img/desfazer.png"))); // NOI18N
		bttCancelar.setText("Cancelar");
		bttCancelar.addActionListener(e -> bttCancelarActionPerformed(e));

		bttSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/jtaskmanager/img/salvar.png"))); // NOI18N
		bttSalvar.setText("Salvar");
		bttSalvar.addActionListener(e -> bttSalvarActionPerformed(e));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(bttCancelar).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(bttSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 92,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(41, 41, 41)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup().addContainerGap()
						.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(bttCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 28,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(bttSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 28,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));

		pack();
	}

	private void initBindings() {
		var bindingGroup = new BindingGroup();

		bindingGroup.addBinding(Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, this,
				ELProperty.create("${model.strNome}"), txtNome, BeanProperty.create("text")));

		bindingGroup.addBinding(SwingBindings.createJComboBoxBinding(AutoBinding.UpdateStrategy.READ_WRITE, this,
				ELProperty.create("${model.strCategoria}"), cbxCategoria));

		bindingGroup.addBinding(Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, this,
				ELProperty.create("${model.strDateCriacao}"), ftxDataCriacao, BeanProperty.create("text")));

		bindingGroup.addBinding(Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, this,
				ELProperty.create("${model.strDescricaoTarefa}"), txaDescricaoTarefa, BeanProperty.create("text")));

		bindingGroup.addBinding(Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, this,
				ELProperty.create("${model.strDescricaoCriterio}"), txaDescricaoCriterio, BeanProperty.create("text")));

		bindingGroup.bind();

	}

	private javax.swing.JButton bttCancelar;
	private javax.swing.JButton bttExcluir;
	private javax.swing.JButton bttIncluir;
	private javax.swing.JButton bttSalvar;
	private javax.swing.JComboBox<String> cbxCategoria;
	private javax.swing.JFormattedTextField ftxDataCriacao;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JTable tabCriterios;
	private javax.swing.JTextArea txaDescricaoCriterio;
	private javax.swing.JTextArea txaDescricaoTarefa;
	private javax.swing.JTextField txtNome;
}
