package br.com.jtaskmanager.controller;

import java.sql.SQLException;
import java.util.List;

import br.com.jtaskmanager.dao.CategoriaDAO;
import br.com.jtaskmanager.dto.CategoriaTreeDTO;
import br.com.jtaskmanager.model.Categoria;

public class CategoriaController {

	private CategoriaDAO dao;

	public CategoriaController() {
		dao = new CategoriaDAO();
	}

	public List<Categoria> findAll() throws SQLException {
		var categorias = dao.findAll();
		if (categorias.isEmpty()) {
			dao.insertIntoTableCategoria();
			categorias = dao.findAll();
		}
		return categorias;
	}
	
	
	public List<CategoriaTreeDTO> getTreeCategoria() throws SQLException {
		return dao.getTreeCategoria();
	}

}
