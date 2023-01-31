package com.telesdev.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.telesdev.model.Usuario;
import com.telesdev.util.ClienteGerenciamentoBibliotecaApi;
import com.telesdev.util.FacesMessages;
import com.telesdev.util.SessionContext;

@Named
@SessionScoped
public class LoginBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	private Usuario usuario;
	
	@Inject
	private FacesMessages messages;
	
	@Inject
	ClienteGerenciamentoBibliotecaApi clienteApi;
	
	public String entrar() {

		System.out.println("Fazendo Login com usuário "
	               + usuario.getNome());

		try {
			if(clienteApi.isAutenticacaoValida(usuario)){
				usuario.setLogado(true);
			    SessionContext.getInstance().setAttribute("usuarioLogado", usuario);
				System.out.println("Usuário Logado com Sucesso. "
			               + usuario.getNome());
				return "index";

			}else {
				messages.error("Usuário/senha inválidos!");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.error("Sistema indisponível!");
			return null;

		}
	}
	
	public String logout() {
		System.out.println("Fazendo logout com usuário "
	               + SessionContext.getInstance().getUsuarioLogado().getNome());
	       SessionContext.getInstance().encerrarSessao();
	       messages.info("Logout realizado com sucesso !");
	       return "login";
	}


	public Usuario getUsuario() {
		return usuario;
	}

}
