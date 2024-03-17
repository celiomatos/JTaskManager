package br.com.jtaskmanager.filter;

import java.util.Date;

public class TarefaFilter {

	private String nome;
	private Date dataCriacaoInicial;
	private Date dataCriacaoFinal;
	private String ordenarPor = "nome";
	private boolean valid = true;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataCriacaoInicial() {
		return dataCriacaoInicial;
	}

	public void setDataCriacaoInicial(Date dataCriacaoInicial) {
		this.dataCriacaoInicial = dataCriacaoInicial;
	}

	public Date getDataCriacaoFinal() {
		return dataCriacaoFinal;
	}

	public void setDataCriacaoFinal(Date dataCriacaoFinal) {
		this.dataCriacaoFinal = dataCriacaoFinal;
	}

	public String getOrdenarPor() {
		return ordenarPor;
	}

	public void setOrdenarPor(String ordenarPor) {
		this.ordenarPor = ordenarPor;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

}
