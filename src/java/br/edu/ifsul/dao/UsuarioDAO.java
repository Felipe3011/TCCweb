/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.Query;

/**
 *
 * @author Felipe
 */
@Stateful
public class UsuarioDAO<TIPO> extends DAOGenerico<Usuario> implements Serializable{
    
    public UsuarioDAO(){
        super();
        classePersistente = Usuario.class;
    }
    
    public boolean verificaUnicidadeNomeUsuario(String login) throws Exception{
        String jpql = "from Usuario where login = :plogin";
        System.out.println("QUERY: " +jpql);
        Query query = em.createQuery(jpql);
            query.setParameter("plogin", login);
        if(query.getResultList().size() > 0){
            return false;
        } else {
            return true;
        }
    }

public Usuario getObjectById(Object id) throws Exception {
        Usuario obj = em.find(Usuario.class, id);
        // Inicializando a colecao para nao dar erro de lazy inicialization exception
        obj.getPermissoes().size();
        return obj;
    } 

}