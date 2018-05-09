package br.com.caelum.livraria.util;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

public class JsfUtil {
	@Produces
	@RequestScoped
	public FacesContext getFacesContext() {
		return  FacesContext.getCurrentInstance();
	}
}
