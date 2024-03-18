package br.com.jtaskmanager.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.jtaskmanager.connect.SQLiteConnection;
import br.com.jtaskmanager.filter.TarefaFilter;
import br.com.jtaskmanager.model.Categoria;
import br.com.jtaskmanager.model.Tarefa;
import br.com.jtaskmanager.util.Utils;

public class TarefaDAO {

	public Integer save(Tarefa tarefa) throws SQLException, NullPointerException {
		var sql = new StringBuilder("INSERT INTO tb_tarefa");
		sql.append("(nome, descricao, id_categoria, data_criacao, status) ");
		sql.append("VALUES(?, ?, ?, ?, ?);");

		var id = 0;

		try (var conn = SQLiteConnection.connect(); var pstm = conn.prepareStatement(sql.toString())) {
			pstm.setString(1, tarefa.getNome());
			pstm.setString(2, tarefa.getDescricao());
			pstm.setInt(3, tarefa.getIdCategoria());
			pstm.setLong(4, Utils.dateToLong(tarefa.getDataCriacao()));
			pstm.setString(5, tarefa.getStatus());
			pstm.execute();
			var rset = pstm.getGeneratedKeys();
			if (rset.next()) {
				id = rset.getInt(1);
			}
		}
		return id;
	}

	public void update(Tarefa tarefa, int id) throws SQLException, NullPointerException {
		var sql = new StringBuilder("UPDATE tb_tarefa ");
		sql.append("SET nome = ?, descricao = ?, ");
		sql.append("id_categoria = ?, data_criacao = ?, status = ? ");
		sql.append("WHERE id = ?;");

		try (var conn = SQLiteConnection.connect(); var pstm = conn.prepareStatement(sql.toString())) {
			pstm.setString(1, tarefa.getNome());
			pstm.setString(2, tarefa.getDescricao());
			pstm.setInt(3, tarefa.getIdCategoria());
			pstm.setLong(4, tarefa.getDataCriacao().getTime());
			pstm.setString(5, tarefa.getStatus());
			pstm.setInt(6, id);
			pstm.execute();
		}
	}

	public void delete(int id) throws SQLException {
		var sql = "DELETE FROM tb_tarefa WHERE id = ? and status != 'concluido';";

		try (var conn = SQLiteConnection.connect(); var pstm = conn.prepareStatement(sql)) {
			pstm.setInt(1, id);
			pstm.execute();
		}
	}

	public Tarefa findById(int id) throws SQLException {
		var sql = "SELECT * FROM tb_tarefa left join tb_categoria on (id_categoria = cat_id) where id = ?;";

		Tarefa tarefa = null;

		try (var conn = SQLiteConnection.connect(); var pstm = conn.prepareStatement(sql)) {
			pstm.setInt(1, id);
			var rset = pstm.executeQuery();
			while (rset.next()) {
				tarefa = new Tarefa();
				tarefa.setId(rset.getInt("id"));
				tarefa.setNome(rset.getString("nome"));
				tarefa.setDescricao(rset.getString("descricao"));
				tarefa.setIdCategoria(rset.getInt("id_categoria"));
				tarefa.setCategoria(new Categoria());
				tarefa.getCategoria().setId(rset.getInt("cat_id"));
				tarefa.getCategoria().setNome(rset.getString("cat_nome"));
				tarefa.setDataCriacao(Utils.longToDate(rset.getLong("data_criacao")));
				tarefa.setStatus(rset.getString("status"));
			}
		}
		return tarefa;
	}

	public List<Tarefa> findAll(TarefaFilter filter) throws SQLException {
		var sql = getFilterSql(filter);

		var tarefas = new ArrayList<Tarefa>();

		try (var conn = SQLiteConnection.connect(); var pstm = conn.prepareStatement(sql)) {
			var rset = pstm.executeQuery();
			while (rset.next()) {
				Tarefa tarefa = new Tarefa();
				tarefa.setId(rset.getInt("id"));
				tarefa.setNome(rset.getString("nome"));
				tarefa.setDescricao(rset.getString("descricao"));
				tarefa.setIdCategoria(rset.getInt("id_categoria"));
				tarefa.setCategoria(new Categoria());
				tarefa.getCategoria().setId(rset.getInt("cat_id"));
				tarefa.getCategoria().setNome(rset.getString("cat_nome"));
				tarefa.setDataCriacao(Utils.longToDate(rset.getLong("data_criacao")));
				tarefa.setStatus(rset.getString("status"));
				tarefas.add(tarefa);
			}
		}
		return tarefas;
	}

	private String getFilterSql(TarefaFilter filter) {
		var sql = new StringBuilder("SELECT * FROM tb_tarefa ");
		sql.append("left join tb_categoria on (id_categoria = cat_id) ");
		var clausula = "where ";

		if (Utils.isNotEmpty(filter.getNome())) {
			sql.append(clausula);
			sql.append("lower(nome) like '%");
			sql.append(filter.getNome().toLowerCase());
			sql.append("%'");
			clausula = " and ";
		}

		if (Utils.isNotNull(filter.getDataCriacaoInicial())) {
			sql.append(clausula);
			sql.append("data_criacao >= ");
			sql.append(filter.getDataCriacaoInicial().getTime());
			clausula = " and ";
		}

		if (Utils.isNotNull(filter.getDataCriacaoFinal())) {
			sql.append(clausula);
			sql.append("data_criacao <= ");
			sql.append(filter.getDataCriacaoFinal().getTime());
		}
		sql.append(" order by ");
		sql.append(filter.getOrdenarPor());

		return sql.toString();
	}

}
