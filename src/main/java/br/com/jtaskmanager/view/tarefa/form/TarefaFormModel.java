package br.com.jtaskmanager.view.tarefa.form;

import java.util.List;

import br.com.jtaskmanager.shared.AbstractModel;

public class TarefaFormModel extends AbstractModel {

	private String strNome;
	private List<String> strCategoria;
	private String strDateCriacao;
	private String strDescricaoTarefa;
	private String strDescricaoCriterio;

	public String getStrNome() {
		return strNome;
	}

	public void setStrNome(String strNome) {
		var old = getStrNome();
		this.strNome = strNome;
		fireProperty("strNome", old, strNome);
	}

	public List<String> getStrCategoria() {
		return strCategoria;
	}

	public void setStrCategoria(List<String> strCategoria) {
		var old = getStrCategoria();
		this.strCategoria = strCategoria;
		fireProperty("strCategoria", old, strCategoria);
	}

	public String getStrDateCriacao() {
		return strDateCriacao;
	}

	public void setStrDateCriacao(String strDateCriacao) {
		var old = getStrDateCriacao();
		this.strDateCriacao = strDateCriacao;
		fireProperty("strDateCriacao", old, strDateCriacao);
	}

	public String getStrDescricaoTarefa() {
		return strDescricaoTarefa;
	}

	public void setStrDescricaoTarefa(String strDescricaoTarefa) {
		var old = getStrDescricaoTarefa();
		this.strDescricaoTarefa = strDescricaoTarefa;
		fireProperty("strDescricaoTarefa", old, strDescricaoTarefa);
	}

	public String getStrDescricaoCriterio() {
		return strDescricaoCriterio;
	}

	public void setStrDescricaoCriterio(String strDescricaoCriterio) {
		var old = getStrDescricaoCriterio();
		this.strDescricaoCriterio = strDescricaoCriterio;
		fireProperty("strDescricaoCriterio", old, strDescricaoCriterio);
	}

}
