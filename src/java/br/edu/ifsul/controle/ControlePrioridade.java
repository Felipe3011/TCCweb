/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.PrioridadeDAO;
import br.edu.ifsul.modelo.Prioridade;
import br.edu.ifsul.util.Util;
import br.edu.ifsul.util.UtilRelatorios;
import java.io.Serializable;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Felipe
 */
@Named(value = "controlePrioridade")
@ViewScoped
public class ControlePrioridade implements Serializable {
    
    @EJB
    private PrioridadeDAO<Prioridade> dao;
    private Prioridade objeto;
    private Boolean editando;

    public ControlePrioridade() {
        editando = false;
    }
    
    public String listar(){
        editando = false;
        return "/privado/prioridade/listar?faces-redirect-true";
    }
    
    public void novo(){
        objeto = new Prioridade();
        editando = true;
    }
    
    public void alterar(Object id){
        try{
            System.out.println("OBJETO: " + id);
            objeto = dao.getObjectById(id);
            editando = true;
        } catch (Exception e){
            Util.mensagemErro("Erro ao recuperar objeto: " + Util.getMensagemErro(e));
        }        
    }
    
    public void excluir(Object id){
        try{
            objeto = dao.getObjectById(id);
            dao.remove(objeto);
            Util.mensagemInformacao("Objeto removido com sucesso");
        } catch (Exception e){
            Util.mensagemErro("Erro ao remover objeto: " + Util.getMensagemErro(e));
        }
    }
    
    public void salvar(){
        try{
            if(objeto.getId() == null){
                dao.persist(objeto);
            }else{
                dao.merge(objeto);
            }
            Util.mensagemInformacao("Objeto persistido com sucesso!");
            editando = false;
        }catch (Exception e){
            Util.mensagemErro("Erro ao persistir objeto: " + Util.getMensagemErro(e));
        }
    }
    
    public void imprimeRelatorioPrioridade(){ 
        HashMap parametros = new HashMap();
        UtilRelatorios.imprimeRelatorio("relatorioPrioridade", parametros, dao.getListaTodos());
    }

    public PrioridadeDAO<Prioridade> getDao() {
        return dao;
    }

    public void setDao(PrioridadeDAO<Prioridade> dao) {
        this.dao = dao;
    }

    public Prioridade getObjeto() {
        return objeto;
    }

    public void setObjeto(Prioridade objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }
    
    
    
}
