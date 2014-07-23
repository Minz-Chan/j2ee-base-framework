package com.iisi.util;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

public class CharacterEncodingFilter implements Filter {
	
	/**
     * 編碼方式：由web.xml裡的init-param指定。
     */
    protected String encoding;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterchain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		req.setCharacterEncoding(encoding);
		filterchain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		this.encoding = null;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.encoding = filterConfig.getInitParameter("encoding");
	}
	
}
