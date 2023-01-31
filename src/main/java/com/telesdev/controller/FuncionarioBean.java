package com.telesdev.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.telesdev.model.Endereco;
import com.telesdev.model.Funcionario;
import com.telesdev.model.Usuario;
import com.telesdev.service.EnderecoService;
import com.telesdev.service.FuncionarioService;
import com.telesdev.util.FacesMessages;
import com.telesdev.util.SessionContext;

@Named
@ViewScoped
public class FuncionarioBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<Funcionario> funcionarios;
	
	private Funcionario funcionarioEdicao = new Funcionario();
	
	private Funcionario funcionarioSelecionado;
	
	private List<Endereco> enderecos = new ArrayList<>();

	
	@Inject
	private FacesMessages messages;
	
	@Inject
	private FuncionarioService funcionarioService;
	
	@Inject
	private EnderecoService enderecoService;

	@PostConstruct
	public void init() {
		funcionarioService.setUsuario((Usuario) SessionContext.getInstance().getAttribute("usuarioLogado"));
		enderecos.add(new Endereco());
		funcionarioEdicao.setEndereco(new Endereco());
	}

	public void salvar() {
		funcionarioService.salvar(funcionarioEdicao);
		messages.info("Funcionário salvo com sucesso!");
        PrimeFaces.current().ajax().update(Arrays.asList("frm-funcionario:msg-funcionarios","frm-funcionario:tabela-funcionarios"));
		prepararNovoCadastro();
		consultarTodos();
	}
	
	public void excluir() {
		funcionarioService.excluir(funcionarioSelecionado);
		funcionarioSelecionado = null;
		consultarTodos();
		messages.info("Funcionário excluído com sucesso!");
	}
	
	public void prepararNovoCadastro() {
		funcionarioEdicao = new Funcionario();
		consultarTodosEnderecos();
	}
	
	public void prepararEdicao() {
		funcionarioEdicao = funcionarioSelecionado;
		consultarTodosEnderecos();
	}
	
	public void consultarTodos() {
		funcionarios = funcionarioService.listar();
	}
	
	public void consultarTodosEnderecos() {
		enderecos = enderecoService.listar();
	}
	
	public Endereco getEndereco(Long id) {
        if (id == null){
            throw new IllegalArgumentException("Sem ID convertido");
        }
        for (Endereco endereco : enderecos){
            if (id.equals(endereco.getId())){
                return endereco;
            }
        }
        return null;
    }

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public Funcionario getFuncionarioEdicao() {
		return funcionarioEdicao;
	}

	public void setFuncionarioEdicao(Funcionario funcionarioEdicao) {
		this.funcionarioEdicao = funcionarioEdicao;
	}

	public Funcionario getFuncionarioSelecionado() {
		return funcionarioSelecionado;
	}

	public void setFuncionarioSelecionado(Funcionario funcionarioSelecionado) {
		this.funcionarioSelecionado = funcionarioSelecionado;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

}
