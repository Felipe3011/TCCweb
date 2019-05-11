/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Equipamento;
import java.io.Serializable;
import javax.ejb.Stateful;
import javax.persistence.Query;

/**
 *
 * @author Felipe
 */
@Stateful
public class EquipamentoDAO<TIPO> extends DAOGenerico<Equipamento> implements Serializable{
    
    public EquipamentoDAO(){
        super();
        classePersistente = Equipamento.class;
    }
    
    public boolean verificaUnicidadePatrimonio(Integer patrimonio) throws Exception{
        System.out.println("VEIO: " + patrimonio);
        String jpql = "from Equipamento where patrimonio = " + patrimonio;
        System.out.println("QUERY: " +jpql);
        Query query = em.createQuery(jpql);
        if(query.getResultList().size() > 0){
            return false;
        } else {
            return true;
        }
    }
    
    public Equipamento getObjectById(Object id) throws Exception {
        Equipamento obj = em.find(Equipamento.class, id);
        // Inicializando a colecao para nao dar erro de lazy inicialization exception
        obj.getConsertos().size();
        return obj;
    } 

}
