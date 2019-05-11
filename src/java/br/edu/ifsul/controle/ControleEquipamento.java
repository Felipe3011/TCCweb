/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.ConsertoDAO;
import br.edu.ifsul.dao.EquipamentoDAO;
import br.edu.ifsul.dao.SetorDAO;
import br.edu.ifsul.modelo.Conserto;
import br.edu.ifsul.modelo.Equipamento;
import br.edu.ifsul.modelo.Setor;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Felipe
 */
@Named(value = "controleEquipamento")
@ViewScoped
public class ControleEquipamento implements Serializable {

    @EJB
    private EquipamentoDAO<Equipamento> dao;
    private Equipamento objeto;
    private Boolean editando;
    
    @EJB
    private SetorDAO<Setor> daoSetor;
    private Boolean novoObjeto;

    @EJB
    private ConsertoDAO<Conserto> daoConserto;
    private Conserto conserto;
    private Boolean novoConserto;
    private Boolean editandoConserto;

    public ControleEquipamento() {
        editando = false;
        editandoConserto = false;
    }
    
    public void verificaUnicidadePatrimonio(){
        if(novoObjeto){
            try{
                if(!dao.verificaUnicidadePatrimonio(objeto.getPatrimonio())){
                    Util.mensagemErro("Equipamento de Patrimônio '" + objeto.getPatrimonio()+ " já existe!");
                    //Capturando o componente que chamou o metodo
                    UIComponent comp = UIComponent.getCurrentComponent(FacesContext.getCurrentInstance());
                    if(comp != null){
                        // se nao for nulo seta o componente como valid false para ficar em vermelho
                        UIInput input = (UIInput) comp;
                        input.setValid(false);
                    }
                }
            } catch (Exception e){
                Util.mensagemErro("Erro do sistema: " + Util.getMensagemErro(e));
            }
        }
    }

    public String listar() {
        editando = false;
        return "/privado/equipamento/listar?faces-redirect-true";
    }

    public void novo() {
        objeto = new Equipamento();
        editando = true;
        novoObjeto = true;
        editandoConserto = false;
    }

    public void alterar(Object id) {
        try {
            objeto = dao.getObjectById(id);
            editando = true;
            novoObjeto = false;
            editandoConserto = false;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao recuperar objeto: " + Util.getMensagemErro(e));
        }
    }

    public void excluir(Object id) {
        try {
            objeto = dao.getObjectById(id);
            dao.remove(objeto);
            Util.mensagemInformacao("Objeto removido com sucesso");
        } catch (Exception e) {
            Util.mensagemErro("Erro ao remover objeto: " + Util.getMensagemErro(e));
        }
    }

    public void salvar() {
        try {
            if (objeto.getPatrimonio() == null) {
                dao.persist(objeto);
            } else {
                dao.merge(objeto);
            }
            Util.mensagemInformacao("Objeto persistido com sucesso!");
            editando = false;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao persistir objeto: " + Util.getMensagemErro(e));
        }
    }

    public void novoConserto() {
        editandoConserto = true;
        conserto = new Conserto();
    }

    public void editarConserto(int index) { 
        conserto = objeto.getConsertos().get(index);
        editandoConserto = true;
    }

    public void salvarConserto() {
        if(editandoConserto){
            objeto.adicionarConserto(conserto);
            Util.mensagemInformacao("Conserto persistido com sucesso!");
        }
        editandoConserto = false;
    }

    public void removerConserto(int index) {
        objeto.removerConserto(index);
        Util.mensagemInformacao("Conserto removido com sucesso!");
    }

    public EquipamentoDAO<Equipamento> getDao() {
        return dao;
    }

    public void setDao(EquipamentoDAO<Equipamento> dao) {
        this.dao = dao;
    }

    public Equipamento getObjeto() {
        return objeto;
    }

    public void setObjeto(Equipamento objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public SetorDAO<Setor> getDaoSetor() {
        return daoSetor;
    }

    public void setDaoSetor(SetorDAO<Setor> daoSetor) {
        this.daoSetor = daoSetor;
    }

    public Boolean getNovoObjeto() {
        return novoObjeto;
    }

    public void setNovoObjeto(Boolean novoObjeto) {
        this.novoObjeto = novoObjeto;
    }

    public ConsertoDAO<Conserto> getDaoConserto() {
        return daoConserto;
    }

    public void setDaoConserto(ConsertoDAO<Conserto> daoConserto) {
        this.daoConserto = daoConserto;
    }

    public Conserto getConserto() {
        return conserto;
    }

    public void setConserto(Conserto conserto) {
        this.conserto = conserto;
    }

    public Boolean getNovoConserto() {
        return novoConserto;
    }

    public void setNovoConserto(Boolean novoConserto) {
        this.novoConserto = novoConserto;
    }

    public Boolean getEditandoConserto() {
        return editandoConserto;
    }

    public void setEditandoConserto(Boolean editandoConserto) {
        this.editandoConserto = editandoConserto;
    }

}
