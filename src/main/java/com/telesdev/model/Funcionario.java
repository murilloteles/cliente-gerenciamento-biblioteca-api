package com.telesdev.model;

import java.util.Date;
import java.util.Objects;

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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(dataContratacao, id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Funcionario other = (Funcionario) obj;
		return Objects.equals(dataContratacao, other.dataContratacao) && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Funcionario [id=" + id + ", dataContratacao=" + dataContratacao + ", getNome()=" + getNome()
				+ ", getNascimento()=" + getNascimento() + ", getNacionalidade()=" + getNacionalidade()
				+ ", getDocumento()=" + getDocumento() + ", getEndereco()=" + getEndereco() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
}
