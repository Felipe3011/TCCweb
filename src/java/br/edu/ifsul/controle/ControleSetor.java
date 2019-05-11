/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.SetorDAO;
import br.edu.ifsul.dao.UnidadeDAO;
import br.edu.ifsul.modelo.Setor;
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
@Named(value = "controleSetor")
@ViewScoped
public class ControleSetor implements Serializable {
    
    @EJB
    private SetorDAO<Setor> dao;
    private Setor objeto;
    private Boolean editando;
    
    @EJB
    private UnidadeDAO<Unidade> daoUnidade;
    private boolean novoObjeto;
    

    public ControleSetor() {
        editando = false;
    }
    
    public String listar(){
        editando = false;
        return "/privado/setor/listar?faces-redirect-true";
    }
    
    public void novo(){
        objeto = new Setor();
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

    public SetorDAO<Setor> getDao() {
        return dao;
    }

    public void setDao(SetorDAO<Setor> dao) {
        this.dao = dao;
    }

    public Setor getObjeto() {
        return objeto;
    }

    public void setObjeto(Setor objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public UnidadeDAO<Unidade> getDaoUnidade() {
        return daoUnidade;
    }

    public void setDaoUnidade(UnidadeDAO<Unidade> daoUnidade) {
        this.daoUnidade = daoUnidade;
    }

    public boolean isNovoObjeto() {
        return novoObjeto;
    }

    public void setNovoObjeto(boolean novoObjeto) {
        this.novoObjeto = novoObjeto;
    }
    
    
    
}
