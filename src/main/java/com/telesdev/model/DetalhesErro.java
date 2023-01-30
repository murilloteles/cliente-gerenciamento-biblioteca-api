package com.telesdev.model;

public class DetalhesErro {
	private String titulo;
	private int status;
	
	public DetalhesErro(String titulo, int status) {
		super();
		this.titulo = titulo;
		this.status = status;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Código: " + status + " - " + titulo;
	}
	
	
}

