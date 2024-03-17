package br.com.jtaskmanager.dao;

import br.com.jtaskmanager.connect.SQLiteConnection;
import br.com.jtaskmanager.model.CriterioAceitacao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CriterioAceitacaoDAO {

	public void save(CriterioAceitacao criterioAceitacao) throws SQLException {
		var sql = new StringBuilder("INSERT INTO tb_criterio_aceitacao");
		sql.append("(descricao, id_tarefa, concluido) ");
		sql.append("VALUES(?, ?, ?);");

		try (var conn = SQLiteConnection.connect(); var pstm = conn.prepareStatement(sql.toString())) {
			pstm.setString(1, criterioAceitacao.getDescricao());
			pstm.setInt(2, criterioAceitacao.getIdTarefa());
			pstm.setBoolean(3, criterioAceitacao.isConcluido());
			pstm.execute();
		}

	}

	public void update(CriterioAceitacao criterioAceitacao, int id) throws SQLException {
		var sql = new StringBuilder("UPDATE tb_criterio_aceitacao ");
		sql.append("SET descricao = ?, concluido = ? ");
		sql.append("WHERE id = ?;");

		try (var conn = SQLiteConnection.connect(); var pstm = conn.prepareStatement(sql.toString())) {
			pstm.setString(1, criterioAceitacao.getDescricao());
			pstm.setBoolean(2, criterioAceitacao.isConcluido());
			pstm.setInt(3, id);
			pstm.execute();
		}
	}

	public void delete(int id) throws SQLException {
		var sql = "DELETE FROM tb_criterio_aceitacao WHERE id = ?;";

		try (var conn = SQLiteConnection.connect(); var pstm = conn.prepareStatement(sql)) {
			pstm.setInt(1, id);
			pstm.execute();
		}
	}

	public void deleteByIdTarefa(int idTarefa) throws SQLException {
		var sql = "DELETE FROM tb_criterio_aceitacao WHERE id_tarefa = ?;";

		try (var conn = SQLiteConnection.connect(); var pstm = conn.prepareStatement(sql)) {
			pstm.setInt(1, idTarefa);
			pstm.execute();
		}
	}

	public List<CriterioAceitacao> findAll(int idTarefa) throws SQLException {
		var sql = "SELECT * FROM tb_criterio_aceitacao WHERE id_tarefa = ?;";

		var criteriosAceitacao = new ArrayList<CriterioAceitacao>();

		try (var conn = SQLiteConnection.connect(); var pstm = conn.prepareStatement(sql)) {
			pstm.setInt(1, idTarefa);
			var rset = pstm.executeQuery();
			while (rset.next()) {
				CriterioAceitacao criterio = new CriterioAceitacao();
				criterio.setId(rset.getInt("id"));
				criterio.setDescricao(rset.getString("descricao"));
				criterio.setIdTarefa(rset.getInt("id_tarefa"));
				criterio.setConcluido(rset.getBoolean("concluido"));

				criteriosAceitacao.add(criterio);
			}
		}
		return criteriosAceitacao;
	}

}
