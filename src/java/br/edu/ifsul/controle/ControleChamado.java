/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.ChamadoDAO;
import br.edu.ifsul.dao.UsuarioDAO;
import br.edu.ifsul.modelo.Chamado;
import br.edu.ifsul.modelo.Usuario;
import br.edu.ifsul.util.Util;
import br.edu.ifsul.util.UtilRelatorios;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
@Named(value = "controleChamado")
@ViewScoped
public class ControleChamado implements Serializable {

    @EJB
    private ChamadoDAO<Chamado> dao;
    private Chamado objeto;
    private Boolean editando;

    @EJB
    private UsuarioDAO<Usuario> daoUsuario;
    private Usuario usuario;
    private Boolean novoObjeto;    
       
    private Calendar data = Calendar.getInstance();

    public ControleChamado() {
        editando = false;
    }
    
    public void verificaCampos(Integer id){        
            try{
                if(!dao.verificaResolucao(id)){
                    Util.mensagemErro("Favor, descrever a resolução do chamado!");
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
    
    public void relatorioNaoFinalizados(){ 
        HashMap parametros = new HashMap();
        UtilRelatorios.imprimeRelatorio("relatorioChamados", parametros, dao.getListaNaoFinalizados());
    }
    
    public void relatorioFinalizados(){ 
        HashMap parametros = new HashMap();
        UtilRelatorios.imprimeRelatorio("relatorioChamados", parametros, dao.getListaFinalizados());
    }
    
    public void relatorioTodos(){ 
        HashMap parametros = new HashMap();
        UtilRelatorios.imprimeRelatorio("relatorioChamados", parametros, dao.getListaTodos());
    }

    public String listar() {
        editando = false;
        return "/privado/chamado/listar?faces-redirect-true";
    }
    
    public String listarSupervisor() {
        editando = false;
        return "/privado/chamado/listarSupervisor?faces-redirect-true";
    }
    
    public String listarFinalizados() {
        editando = false;
        return "/privado/chamado/listarFinalizados?faces-redirect-true";
    }

    public void novo() throws Exception {
        objeto = new Chamado();        
        editando = true;
        String status = "Aguardando Técnico";
        objeto.setStatus(status);
        objeto.setFinalizado(false);
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
    
    public void finalizar(Object id){
        try{
            objeto = dao.getObjectById(id);
            objeto.setFinalizado(true);
            dao.merge(objeto);
            Util.mensagemInformacao("Objeto finalizado com sucesso");
        } catch (Exception e){
            Util.mensagemErro("Erro ao finalizar objeto: " + Util.getMensagemErro(e));
        }
    }

    public void salvar() {
        try {
            if (objeto.getId() == null) {
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
    
    public void setaDataAbertura() {
        Date date = new Date();
        data.setTime(date);
        objeto.setDataAbertura(data);
    }
    
    public ChamadoDAO<Chamado> getDao() {
        return dao;
    }

    public void setDao(ChamadoDAO<Chamado> dao) {
        this.dao = dao;
    }

    public Chamado getObjeto() {
        return objeto;
    }

    public void setObjeto(Chamado objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public UsuarioDAO<Usuario> getDaoUsuario() {
        return daoUsuario;
    }

    public void setDaoUsuario(UsuarioDAO<Usuario> daoUsuario) {
        this.daoUsuario = daoUsuario;
    }

    public Boolean getNovoObjeto() {
        return novoObjeto;
    }

    public void setNovoObjeto(Boolean novoObjeto) {
        this.novoObjeto = novoObjeto;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
