package br.com.jtaskmanager.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.jtaskmanager.connect.SQLiteConnection;
import br.com.jtaskmanager.dto.CategoriaTreeDTO;
import br.com.jtaskmanager.model.Categoria;

public class CategoriaDAO {

	public List<Categoria> findAll() throws SQLException {
		var sql = "SELECT * FROM tb_categoria order by cat_nome;";

		var categorias = new ArrayList<Categoria>();

		try (var conn = SQLiteConnection.connect(); var pstm = conn.prepareStatement(sql)) {
			var rset = pstm.executeQuery();
			while (rset.next()) {
				Categoria categoria = new Categoria();
				categoria.setId(rset.getInt("cat_id"));
				categoria.setNome(rset.getString("cat_nome"));

				categorias.add(categoria);
			}
		}
		return categorias;
	}

	public void insertIntoTableCategoria() throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT OR REPLACE INTO tb_categoria (cat_nome)");
		sql.append("VALUES ('Solicitacao'), ('Incidente'), ('Compra');");

		try (var conn = SQLiteConnection.connect(); var pstm = conn.prepareStatement(sql.toString())) {
			pstm.execute();
		}
	}

	public List<CategoriaTreeDTO> getTreeCategoria() throws SQLException {

		var sql = new StringBuilder();
		sql.append("SELECT cat_nome, nome, c.descricao as descricao FROM tb_criterio_aceitacao c ");
		sql.append("inner join tb_tarefa t on (c.id_tarefa = t.id) ");
		sql.append("inner join tb_categoria on (id_categoria = cat_id) ");
		sql.append("where status != 'concluido' ");
		sql.append("order by cat_nome, nome;");

		var categorias = new ArrayList<CategoriaTreeDTO>();

		try (var conn = SQLiteConnection.connect(); var pstm = conn.prepareStatement(sql.toString())) {
			var rset = pstm.executeQuery();
			while (rset.next()) {
				var dto = new CategoriaTreeDTO();
				dto.setCategoria(rset.getString("cat_nome"));
				dto.setTarefa(rset.getString("nome"));
				dto.setCriterio(rset.getString("descricao"));

				categorias.add(dto);
			}
		}
		return categorias;
	}
}