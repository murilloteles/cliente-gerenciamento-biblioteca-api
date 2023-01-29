package com.telesdev.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.inject.Named;

import com.google.gson.Gson;
import com.telesdev.model.Endereco;
import com.telesdev.model.Usuario;

@Named
public class ClienteGerenciamentoBibliotecaApi implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static String ENDERECO_SERVIDOR = "http://localhost:8082"; 
	private static String URN_BASE = "/biblioteca";
	private static String URN_SERVICOS_ENDERECO = "/endereco";

	private static int HTTP_COD_SUCESSO = 200;
	private static int HTTP_COD_CRIATED = 201;
	private static int HTTP_COD_NO_CONTENT = 204;
	private static int HTTP_COD_BAD_REQUEST = 400;
	private static int HTTP_COD_UNAUTHORIZED = 401;
	private static int HTTP_COD_NOT_FOUND = 404;
	private static int HTTP_COD_ERROR_SERVER = 500;
	
	private static String POST = "POST";
	private static String GET = "GET";
	private static String PUT = "PUT";
	private static String DELETE = "DELETE";

	private Usuario usuarioSession;

	public List<Endereco> listarEnderecos(){
		try {
			String url = ENDERECO_SERVIDOR + URN_BASE + URN_SERVICOS_ENDERECO + "?pagina=0&tamanho=1";

			HttpURLConnection conn = realizarRequest(GET, url, usuarioSession);
			List<Endereco> listEndereco = new ArrayList<>();
		    Gson gson = new Gson();
		    
			String jsonArrayEndereco = getLinhaResultadoPaginacao(conn.getInputStream());
        	Endereco[] enderecoArray = gson.fromJson(jsonArrayEndereco, Endereco[].class);
        	listEndereco.addAll(Arrays.asList(enderecoArray));
        	System.out.println(listEndereco);
			
			return listEndereco;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	public String getLinhaResultadoPaginacao(InputStream is) throws IOException {
		BufferedReader br = null;
	    br = new BufferedReader(new InputStreamReader(is));
	    String strCurrentLine;
        while ((strCurrentLine = br.readLine()) != null) {
        	return strCurrentLine.substring(11,strCurrentLine.indexOf(",\"pageable"));
        }
        return null;
	}
	
	public  boolean isAutenticacaoValida(Usuario usuario) throws MalformedURLException, ProtocolException, IOException {
		this.usuarioSession = usuario;
		String url = ENDERECO_SERVIDOR + URN_BASE + URN_SERVICOS_ENDERECO + "?pagina=0&tamanho=1";

		HttpURLConnection conn = realizarRequest(GET, url, usuarioSession);
		
		if (conn.getResponseCode() == HTTP_COD_UNAUTHORIZED) {
			System.out.println("Não autorizado!");
			return false;
		}else if(conn.getResponseCode() == HTTP_COD_ERROR_SERVER) {
			System.out.println("Sistema Fora do ar!");
			return false;
		}
		
		return true;
	}

	private HttpURLConnection realizarRequest(String method, String urlRequest, Usuario usuario)
			throws MalformedURLException, IOException, ProtocolException {
		
		URL url = new URL(urlRequest);
		URLConnection connection = url.openConnection();
		HttpURLConnection conn = (HttpURLConnection) connection;

		String basicAuth = Base64.getEncoder().encodeToString((usuario.getNome()+":"+usuario.getSenha()).getBytes(StandardCharsets.UTF_8));
		conn.setRequestProperty ("Authorization", "Basic "+basicAuth);

		conn.setRequestMethod(method);
		return conn;
	}

	public Usuario getUsuarioSession() {
		return usuarioSession;
	}

	public void setUsuarioSession(Usuario usuarioSession) {
		this.usuarioSession = usuarioSession;
	}
	
}
