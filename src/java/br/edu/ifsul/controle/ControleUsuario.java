/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.PermissaoDAO;
import br.edu.ifsul.dao.SetorDAO;
import br.edu.ifsul.dao.UsuarioDAO;
import br.edu.ifsul.modelo.Permissao;
import br.edu.ifsul.modelo.Setor;
import br.edu.ifsul.modelo.Usuario;
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
@Named(value = "controleUsuario")
@ViewScoped
public class ControleUsuario implements Serializable {
    
    @EJB
    private UsuarioDAO<Usuario> dao;
    private Usuario objeto;
    private Boolean editando;
    
    @EJB
    private SetorDAO<Setor> daoSetor;
    private Boolean novoObjeto;
    
    @EJB
    private PermissaoDAO<Permissao> daoPermissao;
    private Permissao permissao;
    private Boolean editandoPermissao;

    public ControleUsuario() {
        editando = false;
        editandoPermissao = false;
    }
    
    public void verificaUnicidadeUsuario(){
        if(novoObjeto){
            try{
                if(!dao.verificaUnicidadeNomeUsuario(objeto.getLogin())){
                    Util.mensagemErro("Nome de usuário '" + objeto.getLogin()+ " já existe!");
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
    
    public String listar(){
        editando = false;
        return "/privado/usuario/listar?faces-redirect-true";
    }
    
    public void novo(){
        objeto = new Usuario();
        editando = true;
        novoObjeto = true;
        editandoPermissao = false;
    }
    
    public void alterar(Object id){
        try{
            objeto = dao.getObjectById(id);
            editando = true;
            novoObjeto = false;
            editandoPermissao = false;
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
            if(novoObjeto){
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
    
    public void novaPermissao(){
        editandoPermissao = true;
    }
    
    public void salvarPermissao(){
        if(objeto.getPermissoes().contains(permissao)){
            Util.mensagemErro("Usuario ja possui esta permissao!");
        } else {
            objeto.getPermissoes().add(permissao);
            Util.mensagemInformacao("Permissão adicionada com sucesso!");
        }
        editandoPermissao = false;
    }
    
    public void removerPermissao(Permissao obj){
        System.out.println("PERMISSAO " + obj.getNome());
        objeto.getPermissoes().remove(obj);
        Util.mensagemInformacao("Permissão removida com sucesso!");
    }

    public UsuarioDAO<Usuario> getDao() {
        return dao;
    }

    public void setDao(UsuarioDAO<Usuario> dao) {
        this.dao = dao;
    }

    public Usuario getObjeto() {
        return objeto;
    }

    public void setObjeto(Usuario objeto) {
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

    public PermissaoDAO<Permissao> getDaoPermissao() {
        return daoPermissao;
    }

    public void setDaoPermissao(PermissaoDAO<Permissao> daoPermissao) {
        this.daoPermissao = daoPermissao;
    }

    public Permissao getPermissao() {
        return permissao;
    }

    public void setPermissao(Permissao permissao) {
        this.permissao = permissao;
    }

    public Boolean getEditandoPermissao() {
        return editandoPermissao;
    }

    public void setEditandoPermissao(Boolean editandoPermissao) {
        this.editandoPermissao = editandoPermissao;
    }
    
    
    
}
