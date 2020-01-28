package br.com.caelum.livraria.tx;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Transactional
@Interceptor
public class TransactionManagement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	@AroundInvoke
	public Object treatTransaction(InvocationContext context) throws Exception {
		manager.getTransaction().begin();
		
		Object result = context.proceed();
		
		manager.getTransaction().commit();
		
		return result;
	}
}