/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Prioridade;
import java.io.Serializable;
import javax.ejb.Stateful;

/**
 *
 * @author Felipe
 */
@Stateful
public class PrioridadeDAO<TIPO> extends DAOGenerico<Prioridade> implements Serializable{
    
    public PrioridadeDAO(){
        super();
        classePersistente = Prioridade.class;
    }
    
}
