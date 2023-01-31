package com.telesdev.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Named;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.telesdev.model.ClienteResponse;
import com.telesdev.model.DetalhesErro;
import com.telesdev.model.Funcionario;
import com.telesdev.model.Usuario;

@Named
public class ClienteGerenciamentoBibliotecaApi implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  String URN_BASE = "http://localhost:8082/biblioteca/funcionarios";


	private  int HTTP_COD_SUCESSO = 200;
	private  int HTTP_COD_CRIATED = 201;
	private  int HTTP_COD_NO_CONTENT = 204;
	private  int HTTP_COD_BAD_REQUEST = 400;
	private  int HTTP_COD_UNAUTHORIZED = 401;
	private  int HTTP_COD_NOT_FOUND = 404;
	private  int HTTP_COD_ERROR_SERVER = 500;
	
	private  String POST = "POST";
	private  String GET = "GET";
	private  String PUT = "PUT";
	private  String DELETE = "DELETE";

	private  Usuario usuarioSession;
	
	public  ClienteResponse salvarFuncionario(Funcionario funcionario){
	    ClienteResponse response;
		try {
		    Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		    String funcionarioJson = gson.toJson(funcionario);
			
			HttpURLConnection conn = realizarRequest(POST, URN_BASE, true, funcionarioJson);
			
			if (conn.getResponseCode() == HTTP_COD_CRIATED) {
				response = new ClienteResponse(true);
			}else {
				response = getResponseError(conn.getResponseCode());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			DetalhesErro erro = new DetalhesErro("Sistema indisponível!", HTTP_COD_ERROR_SERVER);
			response = new ClienteResponse(false, erro);
		} 
		
		return response;
	}
	
	public  ClienteResponse alterarFuncionario(Funcionario funcionario){
	    ClienteResponse response;
		try {
		    Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		    String funcionarioJson = gson.toJson(funcionario);
			String url = URN_BASE + "/" + funcionario.getId();
			
			HttpURLConnection conn = realizarRequest(PUT, url, true, funcionarioJson);
			
			if (conn.getResponseCode() == HTTP_COD_NO_CONTENT) {
				response = new ClienteResponse(true);
			}else {
				response = getResponseError(conn.getResponseCode());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			DetalhesErro erro = new DetalhesErro("Sistema indisponível!", HTTP_COD_ERROR_SERVER);
			response = new ClienteResponse(false, erro);
		} 
		
		return response;
	}
	
	public  ClienteResponse deletarFuncionario(long idFuncionario){
	    ClienteResponse response;
		try {
			String url = URN_BASE + "/" + idFuncionario;
			HttpURLConnection conn = realizarRequest(DELETE, url, false, null);
			
			if (conn.getResponseCode() == HTTP_COD_NO_CONTENT) {
				response = new ClienteResponse(true);
			}else {
				response = getResponseError(conn.getResponseCode());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			DetalhesErro erro = new DetalhesErro("Sistema indisponível!", HTTP_COD_ERROR_SERVER);
			response = new ClienteResponse(false, erro);
		} 
		
		return response;
	}
	
	public  ClienteResponse buscarFuncionario(String nomeSearch){
	    ClienteResponse response;
		try {
			String url = URN_BASE + "/buscar?";
			Map<String, String> requestParams = new HashMap<>();
		    requestParams.put("nome", nomeSearch);
		    
		    String encodedURL = requestParams.keySet().stream()
		      .map(key -> key + "=" + encodeValue(requestParams.get(key)))
		      .collect(Collectors.joining("&", url, ""));
		    
		    System.out.println(encodedURL);
		    
			HttpURLConnection conn = realizarRequest(GET, encodedURL, false, null);
			
			if (conn.getResponseCode() == HTTP_COD_SUCESSO) {
			    Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
				String stringResultadoFuncionario = getStringResultadoFuncionario(conn);
				Funcionario funcionarioReturn =  gson.fromJson(stringResultadoFuncionario, Funcionario.class);
				response = new ClienteResponse(true, funcionarioReturn);
				
			}else {
				response = getResponseError(conn.getResponseCode());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			DetalhesErro erro = new DetalhesErro("Sistema indisponível!", HTTP_COD_ERROR_SERVER);
			response = new ClienteResponse(false, erro);
		} 
		
		return response;
	}
	private String encodeValue(String value) {
	    try {
			return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public List<Funcionario> listarFuncionarios(){
		List<Funcionario> funcionarios = new ArrayList<>();
		try {
			String url = URN_BASE ;
		    Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		    
			HttpURLConnection conn = realizarRequest(GET, url, false, null);
			String stringResultadoFuncionario = getStringResultadoFuncionario(conn);
			
			String jsonArray = stringResultadoFuncionario.substring(11,stringResultadoFuncionario.indexOf(",\"pageable"));
		    Funcionario[] funcionarioArray = gson.fromJson(jsonArray, Funcionario[].class);
		    funcionarios.addAll(Arrays.asList(funcionarioArray));

		} catch (Exception e) {
			e.printStackTrace();
		} 
		return funcionarios;
	}
	
	public  boolean isAutenticacaoValida(Usuario usuario) throws MalformedURLException, ProtocolException, IOException {
		this.usuarioSession = usuario;
		String url = URN_BASE + "?pagina=0&tamanho=1";

		HttpURLConnection conn = realizarRequest(GET, url, false, null);
		
		if (conn.getResponseCode() == HTTP_COD_UNAUTHORIZED) {
			System.out.println("Não autorizado!");
			return false;
		}else if(conn.getResponseCode() == HTTP_COD_ERROR_SERVER) {
			System.out.println("Sistema Fora do ar!");
			return false;
		}
		
		return true;
	}

	private  HttpURLConnection realizarRequest(String method, String urlRequest, boolean existeRequestBody, String jsonStringBody)
			throws MalformedURLException, IOException, ProtocolException {
		
		URL url = new URL(urlRequest);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();

		String basicAuth = Base64.getEncoder().encodeToString((usuarioSession.getNome()+":"+usuarioSession.getSenha()).getBytes(StandardCharsets.UTF_8));
		con.setRequestProperty ("Authorization", "Basic "+basicAuth);
		con.setRequestMethod(method);
		if(existeRequestBody) {
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
			try(OutputStream os = con.getOutputStream()) {
			    byte[] input = jsonStringBody.getBytes("utf-8");
			    os.write(input, 0, input.length);			
			}
		}
		return con;
	}
	
	public  String getStringResultadoFuncionario(HttpURLConnection conn) throws IOException {
		BufferedReader br = null;
	    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    String strCurrentLine;
	    String jsonString = "";
	    
        if ((strCurrentLine = br.readLine()) != null) 
        	jsonString = strCurrentLine;
        
        return jsonString;
	}
	
	private  ClienteResponse getResponseError(int responseCode) {
		DetalhesErro erro;
		
		if(responseCode == HTTP_COD_NOT_FOUND) {
			erro = new DetalhesErro("Funcionário não encontrado!", HTTP_COD_NOT_FOUND);
			
		}else if(responseCode == HTTP_COD_BAD_REQUEST) {
			erro = new DetalhesErro("Requisição inválida!", HTTP_COD_BAD_REQUEST);
			
		}else {
			erro = new DetalhesErro("Sistema indisponível!", HTTP_COD_ERROR_SERVER);
		}
		
		return new ClienteResponse(false, erro);
	}

	public Usuario getUsuarioSession() {
		return usuarioSession;
	}

	public void setUsuarioSession(Usuario usuarioSession) {
		this.usuarioSession = usuarioSession;
	}
	
}
