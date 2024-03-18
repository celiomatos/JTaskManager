package br.com.jtaskmanager.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteConnection {
	
	public static String urlBanco = "jdbc:sqlite:testdb.db";

	public static Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection(urlBanco);

		try (Statement stm = conn.createStatement()) {
			stm.execute(createTableCategoria());
			stm.execute(createTableTarefa());
			stm.execute(createTableCriteriosAceitacao());
		}
		return conn;
	}

	public static String createTableCategoria() {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS tb_categoria ( ");
		sql.append("cat_id INTEGER PRIMARY KEY AUTOINCREMENT, ");
		sql.append("cat_nome varchar(20) NOT NULL UNIQUE);");
		return sql.toString();
	}

	public static String createTableTarefa() {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS tb_tarefa ( ");
		sql.append("id INTEGER PRIMARY KEY AUTOINCREMENT, ");
		sql.append("nome varchar(20) NOT NULL,");
		sql.append("descricao varchar(100) NOT NULL,");
		sql.append("id_categoria INTEGER NOT NULL REFERENCES tb_categoria(cat_id),");
		sql.append("data_criacao INTEGER NOT NULL,");
		sql.append("status varchar(20) NOT NULL);");
		return sql.toString();
	}

	public static String createTableCriteriosAceitacao() {
		StringBuilder url = new StringBuilder();
		url.append("CREATE TABLE IF NOT EXISTS tb_criterio_aceitacao ( ");
		url.append("id INTEGER PRIMARY KEY AUTOINCREMENT, ");
		url.append("descricao varchar(100) NOT NULL,");
		url.append("id_tarefa INTEGER NOT NULL REFERENCES tb_tarefa(id),");
		url.append("concluido BOOLEAN NOT NULL);");
		return url.toString();
	}

}
