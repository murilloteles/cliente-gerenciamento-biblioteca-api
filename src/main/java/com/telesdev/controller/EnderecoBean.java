package com.telesdev.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.telesdev.model.Endereco;
import com.telesdev.model.Usuario;
import com.telesdev.service.EnderecoService;
import com.telesdev.util.ClienteGerenciamentoBibliotecaApi;
import com.telesdev.util.FacesMessages;
import com.telesdev.util.SessionContext;

@Named
@ViewScoped
public class EnderecoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EnderecoService enderecoService;
	
	@Inject
	ClienteGerenciamentoBibliotecaApi clienteApi;
	
	@Inject
	private FacesMessages messages;
	
	private List<Endereco> enderecos;
	
	private Endereco enderecoEdicao = new Endereco();
	
	private Endereco enderecoSelecionado;
	
	@PostConstruct
	public void init() {
		clienteApi.setUsuarioSession((Usuario) SessionContext.getInstance().getAttribute("usuarioLogado"));
	}
	
	public void salvar() {
		enderecoService.salvar(enderecoEdicao);
		messages.info("Endereço salvo com sucesso!");
        PrimeFaces.current().ajax().update(Arrays.asList("frm-endereco:msg-endereco","frm-endereco:tabela-enderecos"));
		prepararNovoCadastro();
		consultarTodos();
	}
	
	public void excluir() {
		enderecoService.excluir(enderecoSelecionado);
		enderecoSelecionado = null;
		consultarTodos();
		messages.info("Endereço excluído com sucesso!");
	}
	
	public void prepararNovoCadastro() {
		enderecoEdicao = new Endereco();
	}
	
	public void consultarTodos() {
		enderecos = enderecoService.listar();
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}
	public Endereco getEnderecoEdicao() {
		return enderecoEdicao;
	}
	public void setEnderecoEdicao(Endereco enderecoEdicao) {
		this.enderecoEdicao = enderecoEdicao;
	}

	public Endereco getEnderecoSelecionado() {
		return enderecoSelecionado;
	}

	public void setEnderecoSelecionado(Endereco enderecoSelecionado) {
		this.enderecoSelecionado = enderecoSelecionado;
	}
	
}
