package com.telesdev.model;

import java.util.Date;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class Pessoa {
	
	@NotEmpty(message = "O campo nome não pode ser vazio.")
	private String nome;
	
	@NotNull(message = "Campo nascimento é de preenchimento obrigatório.")
	private Date nascimento;
	
	private String nacionalidade;
	
	@Pattern(regexp="^(([0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2})|([0-9]{11}))$",message="Documento com formato inválido. Formatos aceitos: 00000000000 ou 000.000.000-00 ")
	@NotEmpty(message = "O campo documento não pode ser vazio.")
	private String documento;
	
	@NotNull(message = "endereco é obrigatório.")
	private Endereco endereco;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@Override
	public int hashCode() {
		return Objects.hash(documento, endereco, nacionalidade, nascimento, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		return Objects.equals(documento, other.documento) && Objects.equals(endereco, other.endereco)
				&& Objects.equals(nacionalidade, other.nacionalidade) && Objects.equals(nascimento, other.nascimento)
				&& Objects.equals(nome, other.nome);
	}
	
}
