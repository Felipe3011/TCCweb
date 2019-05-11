/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Chamado;
import java.io.Serializable;
import javax.ejb.Stateful;
import javax.persistence.Query;

/**
 *
 * @author Felipe
 */
@Stateful
public class ChamadoDAO<TIPO> extends DAOGenerico<Chamado> implements Serializable{
    
    public ChamadoDAO(){
        super();
        classePersistente = Chamado.class;
    }
    
    public boolean verificaResolucao(Integer id) throws Exception{
        System.out.println("VEIO: " + id);
        String jpql = "from Chamado where id = " + id;
        System.out.println("QUERY: " +jpql);
        Query query = em.createQuery(jpql);
        System.out.println("QUERY:: " + query.getResultList().size() );
        if(query.getResultList().size() > 0){
            return false;
        } else {
            return true;
        }
    }
}
