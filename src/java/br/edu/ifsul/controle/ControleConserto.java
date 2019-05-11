/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.ConsertoDAO;
import br.edu.ifsul.modelo.Conserto;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Felipe
 */
@Named(value = "controleConserto")
@ViewScoped
public class ControleConserto implements Serializable {
    
    @EJB
    private ConsertoDAO<Conserto> dao;
    private Conserto objeto;
    private Boolean editando;

    public ControleConserto() {
        editando = false;
    }
    
    public String listar(){
        editando = false;
        return "/privado/conserto/listar?faces-redirect-true";
    }
    
    public void novo(){
        objeto = new Conserto();
        editando = true;
    }
    
    public void alterar(Object id){
        try{
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
            System.out.println("CHEGOU: " + objeto.getProblema());
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

    public ConsertoDAO<Conserto> getDao() {
        return dao;
    }

    public void setDao(ConsertoDAO<Conserto> dao) {
        this.dao = dao;
    }

    public Conserto getObjeto() {
        return objeto;
    }

    public void setObjeto(Conserto objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }
    
    
    
}
