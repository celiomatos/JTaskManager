package br.com.jtaskmanager.dto;

public class CategoriaTreeDTO {

	private String categoria;
	private String tarefa;
	private String criterio;

	public CategoriaTreeDTO() {
	}

	public CategoriaTreeDTO(String categoria, String tarefa, String criterio) {
		this.categoria = categoria;
		this.tarefa = tarefa;
		this.criterio = criterio;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getTarefa() {
		return tarefa;
	}

	public void setTarefa(String tarefa) {
		this.tarefa = tarefa;
	}

	public String getCriterio() {
		return criterio;
	}

	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}

}
