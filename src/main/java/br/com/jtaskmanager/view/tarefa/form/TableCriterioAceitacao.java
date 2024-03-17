package br.com.jtaskmanager.view.tarefa.form;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.jtaskmanager.model.CriterioAceitacao;

public class TableCriterioAceitacao extends AbstractTableModel {

	private List<CriterioAceitacao> criterios;
	private String[] columns = { "Descrição", "Concluído" };

	public TableCriterioAceitacao(List<CriterioAceitacao> criterios) {
		this.criterios = criterios;
	}

	@Override
	public int getRowCount() {
		return criterios.size();
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
	public Class<?> getColumnClass(int columnIndex) {
		if (1 == columnIndex) {
			return Boolean.class;
		}
		return super.getColumnClass(columnIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (1 == columnIndex) {
			criterios.get(rowIndex).setConcluido((Boolean) aValue);
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return 1 == columnIndex;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return criterios.get(rowIndex).getDescricao();
		case 1:
			return criterios.get(rowIndex).isConcluido();
		default:
			return "";
		}
	}

}
