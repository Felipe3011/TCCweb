/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Conserto;
import java.io.Serializable;
import javax.ejb.Stateful;

/**
 *
 * @author Felipe
 */
@Stateful
public class ConsertoDAO<TIPO> extends DAOGenerico<Conserto> implements Serializable{
    
    public ConsertoDAO(){
        super();
        classePersistente = Conserto.class;
    }
    
}
