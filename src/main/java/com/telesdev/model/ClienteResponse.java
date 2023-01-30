package com.telesdev.model;

public class ClienteResponse {
	private boolean sucesso;
	private DetalhesErro detalhesErro;
	private Funcionario funcionario;
	
	public ClienteResponse(boolean sucesso) {
		super();
		this.sucesso = sucesso;
	}

	public ClienteResponse(boolean sucesso, DetalhesErro detalhesErro) {
		super();
		this.sucesso = sucesso;
		this.detalhesErro = detalhesErro;
	}

	public ClienteResponse(boolean sucesso, Funcionario funcionario) {
		super();
		this.sucesso = sucesso;
		this.funcionario = funcionario;
	}

	public boolean isSucesso() {
		return sucesso;
	}

	public void setSucesso(boolean sucesso) {
		this.sucesso = sucesso;
	}

	public DetalhesErro getDetalhesErro() {
		return detalhesErro;
	}

	public void setDetalhesErro(DetalhesErro detalhesErro) {
		this.detalhesErro = detalhesErro;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@Override
	public String toString() {
		return "ClienteResponse [sucesso=" + sucesso + ", detalhesErro=" + detalhesErro + ", funcionario=" + funcionario
				+ "]";
	}
	
	
}
