package br.com.jtaskmanager.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tarefa {

	private Integer id;
	private String nome;
	private String descricao;
	private Integer idCategoria;
	private Categoria categoria;
	private Date dataCriacao;
	private String status;
	private List<CriterioAceitacao> criteriosAceitacao;

	public Tarefa() {
		criteriosAceitacao = new ArrayList<>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<CriterioAceitacao> getCriteriosAceitacao() {
		return criteriosAceitacao;
	}

	public void setCriteriosAceitacao(List<CriterioAceitacao> criteriosAceitacao) {
		this.criteriosAceitacao = criteriosAceitacao;
	}

}
