package com.telesdev.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class Funcionario extends Pessoa{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Data de contratação obrigatória.")
	private Date dataContratacao;
	
	public Funcionario() {
		super();
	}

	public Date getDataContratacao() {
		return dataContratacao;
	}

	public void setDataContratacao(Date dataContratacao) {
		this.dataContratacao = dataContratacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Funcionario [id=" + id + ", dataContratacao=" + dataContratacao + ", getNome()=" + getNome()
				+ ", getNascimento()=" + getNascimento() + ", getNacionalidade()=" + getNacionalidade()
				+ ", getDocumento()=" + getDocumento() + ", getEndereco()=" + getEndereco() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
}
