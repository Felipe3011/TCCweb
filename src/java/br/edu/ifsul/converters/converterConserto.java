/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.converters;

import br.edu.ifsul.modelo.Conserto;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Felipe
 */
@FacesConverter(value = "converterConserto")
public class converterConserto implements Serializable, Converter{

    @PersistenceContext(unitName = "PC2-webPU")
    private EntityManager em;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if(string == null || string.equals("Selecione um registro")){
            return null;
        }
        return em.find(Conserto.class, Integer.parseInt(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o == null){
            return null;
        }
        Conserto obj = (Conserto) o;
        return obj.getId().toString();
    }
    
}
