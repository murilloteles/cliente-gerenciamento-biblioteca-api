package com.telesdev.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.telesdev.model.Funcionario;
import com.telesdev.model.Usuario;
import com.telesdev.util.ClienteGerenciamentoBibliotecaApi;
import com.telesdev.util.Transacional;

public class FuncionarioService implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	ClienteGerenciamentoBibliotecaApi clienteApi;
	
	@Transacional
	public void salvar(Funcionario funcionario) {
		clienteApi.salvarFuncionario(funcionario);
	}
	
	@Transacional
	public void alterar(Funcionario funcionario) {
		clienteApi.alterarFuncionario(funcionario);
	}
	
	@Transacional
	public void excluir(Funcionario funcionario) {
		clienteApi.deletarFuncionario(funcionario.getId());
	}
	
	@Transacional
	public void buscar(long idFuncionario) {
		clienteApi.deletarFuncionario(idFuncionario);
	}
	
	public List<Funcionario> listar(){
		return clienteApi.listarFuncionarios();
	}

	public void setUsuario(Usuario user) {
		clienteApi.setUsuarioSession(user);		
	}

}
