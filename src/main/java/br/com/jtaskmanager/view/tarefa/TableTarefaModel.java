package br.com.jtaskmanager.view.tarefa;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.jtaskmanager.model.Tarefa;
import br.com.jtaskmanager.util.Utils;

public class TableTarefaModel extends AbstractTableModel {

	private String[] columns = { "Nome", "Descrição", "Categoria", "Data criação", "Status" };
	private List<Tarefa> tarefas;

	public TableTarefaModel(List<Tarefa> tarefas) {
		super();
		this.tarefas = tarefas;
	}

	@Override
	public int getRowCount() {
		return tarefas.size();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return tarefas.get(rowIndex).getNome();
		case 1:
			return tarefas.get(rowIndex).getDescricao();
		case 2:
			return tarefas.get(rowIndex).getCategoria().getNome();
		case 3:
			return Utils.dateToStr(tarefas.get(rowIndex).getDataCriacao());
		case 4:
			return tarefas.get(rowIndex).getStatus();
		default:
			return "";
		}
	}

}
