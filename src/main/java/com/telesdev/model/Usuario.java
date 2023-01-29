package com.telesdev.model;

import java.io.Serializable;
import java.util.Objects;

public class Usuario implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String senha;
	private boolean logado;
	
	
	public Usuario() {
		super();
	}
	
	public Usuario(String nome, String senha, boolean logado) {
		super();
		this.nome = nome;
		this.senha = senha;
		this.logado = logado;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public boolean isLogado() {
		return logado;
	}
	public void setLogado(boolean logado) {
		this.logado = logado;
	}
	@Override
	public String toString() {
		return "Usuario [nome=" + nome + ", senha=" + senha + ", logado=" + logado + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(logado, nome, senha);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return logado == other.logado && Objects.equals(nome, other.nome) && Objects.equals(senha, other.senha);
	}
	
}
