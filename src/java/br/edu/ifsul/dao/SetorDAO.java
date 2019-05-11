/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Setor;
import java.io.Serializable;
import javax.ejb.Stateful;

/**
 *
 * @author Felipe
 */
@Stateful
public class SetorDAO<TIPO> extends DAOGenerico<Setor> implements Serializable{
    
    public SetorDAO(){
        super();
        classePersistente = Setor.class;
    }
    
}
