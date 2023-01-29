package com.telesdev.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.telesdev.model.Endereco;
import com.telesdev.repository.EnderecoRepository;
import com.telesdev.util.Transacional;

public class EnderecoService implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EnderecoRepository enderecoRepository;
	
	@Transacional
	public void salvar(Endereco endereco) {
		enderecoRepository.guardar(endereco);
	}
	
	@Transacional
	public void excluir(Endereco endereco) {
		enderecoRepository.remover(endereco);
	}
	
	public List<Endereco> listar(){
		return enderecoRepository.todos();
	}

}
