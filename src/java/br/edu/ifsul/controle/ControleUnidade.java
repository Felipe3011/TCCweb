/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.UnidadeDAO;
import br.edu.ifsul.modelo.Unidade;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Felipe
 */
@Named(value = "controleUnidade")
@ViewScoped
public class ControleUnidade implements Serializable {
    
    @EJB
    private UnidadeDAO<Unidade> dao;
    private Unidade objeto;
    private Boolean editando;

    public ControleUnidade() {
        editando = false;
    }
    
    public String listar(){
        editando = false;
        return "/privado/unidade/listar?faces-redirect-true";
    }
    
    public void novo(){
        objeto = new Unidade();
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

    public UnidadeDAO<Unidade> getDao() {
        return dao;
    }

    public void setDao(UnidadeDAO<Unidade> dao) {
        this.dao = dao;
    }

    public Unidade getObjeto() {
        return objeto;
    }

    public void setObjeto(Unidade objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }
    
    
    
}
