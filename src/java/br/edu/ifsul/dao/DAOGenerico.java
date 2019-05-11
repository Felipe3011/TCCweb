/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import java.io.Serializable;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Felipe
 */
public class DAOGenerico<TIPO> implements Serializable{
    
    private List<TIPO> listaObjetos;
    private List<TIPO> listaTodos;
    
    @PersistenceContext(unitName = "PC2-webPU")
    protected EntityManager em;
    protected Class classePersistente;
    
    private String ordem = "id";
    private String filtro = "";
    private Integer maximoObjetos = 10;
    private Integer posicaoAtual = 0;
    private Integer totalObjetos = 0;

    public DAOGenerico() {
    }
    
    public void persist(TIPO obj) throws Exception {
        em.persist(obj);
    }
    
    // Mais de Uma @RolesAllowed({"ADMINISTRADOR", "USUARIO"})
    @RolesAllowed("ADMINISTRADOR")
    public void remove(TIPO obj) throws Exception {
        obj = em.merge(obj); // necessario para criar o vinculo com o banco novamente
        em.remove(obj);
    }
    
    public void merge(TIPO obj) throws Exception {
        em.merge(obj);
    }
    
    public TIPO getObjectById(Object id) throws Exception {
        return (TIPO) em.find(classePersistente, id);
    }

    public List<TIPO> getListaObjetos() {
        String jpql = "from " + classePersistente.getSimpleName();
        String where = "";
        // filtrar a entrada para proteger de injeção de sql
        filtro = filtro.replaceAll("[';-]", "");
        if (filtro.length() > 0) {
            if (ordem.equals("id")) {
                try {
                    Integer.parseInt(filtro);
                    where += " where " + ordem + " = '" + filtro + "' ";
                    System.out.println("String: " + jpql);
                } catch (Exception e) {

                }
            } else {
                where += " where upper(" + ordem + ") like '" + filtro.toUpperCase() + "%' ";
                System.out.println("String: " + jpql);
            }
        }
        jpql += where;
        jpql += " order by " + ordem;
        System.out.println("String: " + jpql);
        totalObjetos = em.createQuery(jpql).getResultList().size();
        return em.createQuery(jpql).
                setFirstResult(posicaoAtual).
                setMaxResults(maximoObjetos).getResultList();
    }
    
    public List<TIPO> getListaFinalizados() {
        String jpql = "from " + classePersistente.getSimpleName();
        String where = "";
        // filtrar a entrada para proteger de injeção de sql
        filtro = filtro.replaceAll("[';-]", "");
        System.out.println("Filtro: " + filtro );
        if (filtro.length() > 0) {
            if (ordem.equals("id")) {
                try {
                    Integer.parseInt(filtro);
                    where += " where finalizado = true and " + ordem + " = '" + filtro + "' ";
                    System.out.println("Filtro2: " + filtro );
                } catch (Exception e) {

                }
            } else {
                where += " where finalizado = true and upper(" + ordem + ") like '" + filtro.toUpperCase() + "%' ";
                System.out.println("Filtro3: " + filtro );
            }
        }
        jpql += where;
        jpql += " order by " + ordem;
        System.out.println("String: " + jpql);
        totalObjetos = em.createQuery(jpql).getResultList().size();
        return em.createQuery(jpql).
                setFirstResult(posicaoAtual).
                setMaxResults(maximoObjetos).getResultList();
    }
    
    public List<TIPO> getListaFinalizadosUsu(String login) {
        String jpql = "from " + classePersistente.getSimpleName() + " where usuario = '" + login + "' and finalizado = true";
        String where = "";
        // filtrar a entrada para proteger de injeção de sql
        filtro = filtro.replaceAll("[';-]", "");
        if (filtro.length() > 0) {
            if (ordem.equals("id")) {
                try {
                    Integer.parseInt(filtro);
                    where += " where " + ordem + " = '" + filtro + "' ";
                } catch (Exception e) {

                }
            } else {
                where += " where upper(" + ordem + ") like '" + filtro.toUpperCase() + "%' ";
            }
        }
        jpql += where;
        jpql += " order by " + ordem;
        totalObjetos = em.createQuery(jpql).getResultList().size();
        return em.createQuery(jpql).
                setFirstResult(posicaoAtual).
                setMaxResults(maximoObjetos).getResultList();
    }
    
    public List<TIPO> getListaNaoFinalizados() {
        String jpql = "from " + classePersistente.getSimpleName() + " where finalizado = false";
        String where = "";
        // filtrar a entrada para proteger de injeção de sql
        filtro = filtro.replaceAll("[';-]", "");
        if (filtro.length() > 0) {
            if (ordem.equals("id")) {
                try {
                    Integer.parseInt(filtro);
                    where += " where " + ordem + " = '" + filtro + "' ";
                } catch (Exception e) {

                }
            } else {
                where += " where upper(" + ordem + ") like '" + filtro.toUpperCase() + "%' ";
            }
        }
        jpql += where;
        jpql += " order by " + ordem;
        totalObjetos = em.createQuery(jpql).getResultList().size();
        return em.createQuery(jpql).
                setFirstResult(posicaoAtual).
                setMaxResults(maximoObjetos).getResultList();
    }
    
        public List<TIPO> getUsuAutenticadoChamados(String login) {
        String jpql = "from " + classePersistente.getSimpleName() + " where usuario = '" + login + "' and finalizado = false";
        String where = "";
        // filtrar a entrada para proteger de injeção de sql
        filtro = filtro.replaceAll("[';-]", "");
        if (filtro.length() > 0) {
            if (ordem.equals("id")) {
                try {
                    Integer.parseInt(filtro);
                    where += " where " + ordem + " = '" + filtro + "' ";
                } catch (Exception e) {

                }
            } else {
                where += " where upper(" + ordem + ") like '" + filtro.toUpperCase() + "%' ";
            }
        }
        jpql += where;
        jpql += " order by " + ordem;
        totalObjetos = em.createQuery(jpql).getResultList().size();
        return em.createQuery(jpql).
                setFirstResult(posicaoAtual).
                setMaxResults(maximoObjetos).getResultList();
    }
        
        public List<TIPO> getUsuAutenticadoUsuario(String login) {
        String jpql = "from " + classePersistente.getSimpleName() + " where login = '" + login + "'";
        String where = "";
        // filtrar a entrada para proteger de injeção de sql
        filtro = filtro.replaceAll("[';-]", "");
        if (filtro.length() > 0) {
            if (ordem.equals("id")) {   
                try {
                    Integer.parseInt(filtro);
                    where += " where " + ordem + " = '" + filtro + "' ";
                } catch (Exception e) {

                }
            } else {
                where += " where upper(" + ordem + ") like '" + filtro.toUpperCase() + "%' ";
            }
        }
        jpql += where;
        jpql += " order by " + ordem;
        totalObjetos = em.createQuery(jpql).getResultList().size();
        return em.createQuery(jpql).
                setFirstResult(posicaoAtual).
                setMaxResults(maximoObjetos).getResultList();
    }
    
    public void primeiro() {
        setPosicaoAtual((Integer) 0);
    }

    public void anterior() {
        setPosicaoAtual((Integer) (getPosicaoAtual() - getMaximoObjetos()));
        if (getPosicaoAtual() < 0) {
            setPosicaoAtual((Integer) 0);
        }
    }

    public void proximo() {
        if (getPosicaoAtual() + getMaximoObjetos() < getTotalObjetos()) {
            setPosicaoAtual((Integer) (getPosicaoAtual() + getMaximoObjetos()));
        }
    }

    public void ultimo() {
        int resto = getTotalObjetos() % getMaximoObjetos();
        if (resto > 0) {
            setPosicaoAtual((Integer) getTotalObjetos() - resto);
        } else {
            setPosicaoAtual((Integer) getTotalObjetos() - getMaximoObjetos());
        }
    }
    
    public String getMensagemNavegacao(){
        int ate = getPosicaoAtual() + getMaximoObjetos();
        if (ate > getTotalObjetos()) {
            ate = getTotalObjetos();
        }
        return "Listando de " + (getPosicaoAtual() + 1) + " até " + ate + 
                " de " + getTotalObjetos() + " registros";
    }
    
    public void rollback() {
        if (em.getTransaction().isActive() == false) {
            em.getTransaction().begin();
        }
        em.getTransaction().rollback();
    }
    
     public TIPO localizar(Object id) {
        rollback();
        TIPO obj = (TIPO) em.find(classePersistente, id);
        return obj;
    }

    public void setListaObjetos(List<TIPO> listaObjetos) {
        this.listaObjetos = listaObjetos;
    }

    public List<TIPO> getListaTodos() {
        String jpql = "from " + classePersistente.getSimpleName();
        return em.createQuery(jpql).getResultList();
    }

    public void setListaTodos(List<TIPO> listaTodos) {
        this.listaTodos = listaTodos;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Class getClassePersistente() {
        return classePersistente;
    }

    public void setClassePersistente(Class classePersistente) {
        this.classePersistente = classePersistente;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public Integer getMaximoObjetos() {
        return maximoObjetos;
    }

    public void setMaximoObjetos(Integer maximoObjetos) {
        this.maximoObjetos = maximoObjetos;
    }

    public Integer getPosicaoAtual() {
        return posicaoAtual;
    }

    public void setPosicaoAtual(Integer posicaoAtual) {
        this.posicaoAtual = posicaoAtual;
    }

    public Integer getTotalObjetos() {
        return totalObjetos;
    }

    public void setTotalObjetos(Integer totalObjetos) {
        this.totalObjetos = totalObjetos;
    }
    
    
    
}
