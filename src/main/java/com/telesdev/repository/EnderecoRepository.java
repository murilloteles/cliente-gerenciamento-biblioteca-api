package com.telesdev.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.telesdev.model.Endereco;

public class EnderecoRepository implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Endereco porId(Long id) {
		return manager.find(Endereco.class, id);
	}
	
	public List<Endereco> todos() {
		return manager.createQuery("from Endereco e order by e.cep", Endereco.class).getResultList();
	}
	
	public Endereco guardar(Endereco endereco) {
		return manager.merge(endereco);
	}
	
	public void remover(Endereco endereco) {
		endereco = porId(endereco.getId());
		manager.remove(endereco);
	}

	

}
