package br.com.jtaskmanager.controller;

import java.sql.SQLException;
import java.util.List;

import br.com.jtaskmanager.dao.CriterioAceitacaoDAO;
import br.com.jtaskmanager.dao.TarefaDAO;
import br.com.jtaskmanager.filter.TarefaFilter;
import br.com.jtaskmanager.model.CriterioAceitacao;
import br.com.jtaskmanager.model.Tarefa;

public class TarefaController {

	private TarefaDAO tarefaDAO;
	private CriterioAceitacaoDAO criterioAceitacaoDAO;

	public TarefaController() {
		tarefaDAO = new TarefaDAO();
		criterioAceitacaoDAO = new CriterioAceitacaoDAO();
	}

	public Integer save(Tarefa tarefa) throws SQLException, NullPointerException {
		var concluido = true;
		for (var criterio : tarefa.getCriteriosAceitacao()) {
			if (concluido) {
				concluido = criterio.isConcluido();
			}
		}
		if (concluido) {
			tarefa.setStatus("concluido");
		} else {
			tarefa.setStatus("novo");
		}

		var idTarefa = tarefaDAO.save(tarefa);
		for (var criterio : tarefa.getCriteriosAceitacao()) {
			criterio.setIdTarefa(idTarefa);
			criterioAceitacaoDAO.save(criterio);
		}
		
		return idTarefa;
	}

	public void update(Tarefa tarefa, List<CriterioAceitacao> criteriosAExcluir) throws SQLException, NullPointerException {
		if (!criteriosAExcluir.isEmpty()) {
			for (var criterio : criteriosAExcluir) {
				criterioAceitacaoDAO.delete(criterio.getId());
			}
		}

		var concluido = true;

		for (var criterio : tarefa.getCriteriosAceitacao()) {
			if (concluido) {
				concluido = criterio.isConcluido();
			}

			if (null == criterio.getId()) {
				criterio.setIdTarefa(tarefa.getId());
				criterioAceitacaoDAO.save(criterio);
			} else {
				criterioAceitacaoDAO.update(criterio, criterio.getId());
			}
		}
		if (concluido) {
			tarefa.setStatus("concluido");
		} else {
			tarefa.setStatus("em andamento");
		}
		tarefaDAO.update(tarefa, tarefa.getId());
	}

	public List<Tarefa> findAll(TarefaFilter filter) throws SQLException {
		return tarefaDAO.findAll(filter);
	}

	public Tarefa findById(int id) throws SQLException {
		var tarefa = tarefaDAO.findById(id);
		if (null != tarefa) {
			tarefa.setCriteriosAceitacao(criterioAceitacaoDAO.findAll(id));
		}
		return tarefa;
	}

	public void delete(int id) throws SQLException {
		criterioAceitacaoDAO.deleteByIdTarefa(id);
		tarefaDAO.delete(id);
	}

}
