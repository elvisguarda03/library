package br.com.caelum.livraria.util;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Log
@Interceptor
public class TimeExecution implements Serializable {
	private static final long serialVersionUID = 1L;

	@AroundInvoke
	public Object treatTime(InvocationContext context) throws Exception {
		long start = System.currentTimeMillis();
		
		Object result = context.proceed();
		
		long end = System.currentTimeMillis();
		
		System.out.println(context.getMethod().getName() + " - Tempo de execu��o:  " + (end - start));
	
		return result;
	}
}
