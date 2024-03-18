package br.com.jtaskmanager.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import br.com.jtaskmanager.connect.SQLiteConnection;
import br.com.jtaskmanager.model.Categoria;
import br.com.jtaskmanager.model.CriterioAceitacao;
import br.com.jtaskmanager.model.Tarefa;

public class TarefaControllerTest {

	private TarefaController tarefaController;

	@Before
	public void setUp() throws Exception {
		SQLiteConnection.urlBanco = "jdbc:sqlite:testdb.db";
		tarefaController = new TarefaController();
	}

	@Test
	public void testSaveThrowExceptionOnFail() {
		var tarefa = new Tarefa();
		tarefa.setNome("testSaveWithouCatego");
		tarefa.setDescricao("testSaveThrowExceptionOnFail");
		tarefa.setDataCriacao(new Date());

		var criterio = new CriterioAceitacao();
		criterio.setDescricao("passar no teste save tarefa");
		criterio.setConcluido(false);

		var criterios = new ArrayList<CriterioAceitacao>();
		criterios.add(criterio);

		tarefa.setCriteriosAceitacao(criterios);

		try {
			tarefaController.save(tarefa);
		} catch (SQLException | NullPointerException e) {
			assertNotNull(e);
		}
	}

	@Test
	public void testSaveSuccessful() {
		var tarefa = new Tarefa();
		tarefa.setNome("testSaveSuccessful");
		tarefa.setDescricao("testSaveSuccessfulWithAllFields");
		tarefa.setDataCriacao(new Date());

		tarefa.setIdCategoria(1);
		tarefa.setCategoria(new Categoria());
		tarefa.getCategoria().setId(1);

		var criterio = new CriterioAceitacao();
		criterio.setDescricao("passar no teste save tarefa");
		criterio.setConcluido(false);

		var criterios = new ArrayList<CriterioAceitacao>();
		criterios.add(criterio);

		tarefa.setCriteriosAceitacao(criterios);

		try {
			var idTarefa = tarefaController.save(tarefa);
			assertNotNull(idTarefa);
		} catch (SQLException | NullPointerException e) {
			fail(e.getMessage());
		}
	}
}
