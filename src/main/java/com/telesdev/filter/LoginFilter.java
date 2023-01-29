package com.telesdev.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.telesdev.model.Usuario;

public class LoginFilter implements Filter {

	 public void doFilter(ServletRequest req, ServletResponse res,
	                    FilterChain chain) throws IOException, ServletException {
		   Usuario user = null;
		   HttpServletResponse response = (HttpServletResponse) res;
		   HttpServletRequest request = (HttpServletRequest) req;		
		   HttpSession sess = ((HttpServletRequest) request).getSession(false);

		   if (sess != null)
		         user = (Usuario) sess.getAttribute("usuarioLogado");
		   
		   if (user != null && user.isLogado()){
		          chain.doFilter(request, response);
		 }else {
			   response.sendRedirect(request.getContextPath() + "/Login.xhtml");
		 }
	
	 }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}