package br.com.caelum.livraria.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Usuario;

@Named
@ViewScoped
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioDao dao;
	
	@Inject
	private FacesContext context;
	private Usuario user = new Usuario();
	
	public Usuario getUser() {
		return user;
	}
	
	public String login() {
		System.out.println("Fazendo login do usuário "
	            + this.user.getEmail());

		ExternalContext externalContext = context.getExternalContext();
		
		if (!dao.existsByEmailAndPass(user.getEmail(), user.getSenha())) {
			//O FacesContext só existe em uma requisição, e com o faces-redirect são feitas 2 requisições.
			//Existe um escopo chamado flash no ExternalContext que surgiu a partir do JSF 2, e este dura duas requisições.
			//Este método mantém a mensagem adicionada por duas requisições.
			externalContext.getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Usuário não encontrado"));
			
			return "login?faces-redirect=true";
		}
		
		externalContext.getSessionMap().put("userLogged", this.user);
		
		return "livro?faces-redirect=true";
	}
	
	public String logout() {
		context.getExternalContext().getSessionMap().remove("userLogged");
		
		return "login?faces-redirect=true";
	}
}