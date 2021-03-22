package libra.servletPlugins;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;

@Component
public class MyFilter_7010 implements Filter{
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("DubboProvider_7010.MyFilter...init");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("DubboProvider_7010.MyFilter...doFilter");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		System.out.println("DubboProvider_7010.MyFilter...destroy");
	}
}