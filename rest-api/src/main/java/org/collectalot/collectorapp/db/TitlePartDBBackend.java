package org.collectalot.collectorapp.db;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

import org.collectalot.collectorapp.model.TitlePart;

@ApplicationScoped
public class TitlePartDBBackend {
    public TitlePart[] getAllTitleParts() {
    	EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("TitlePart.findAllNoParent", TitlePart.class).getResultList().toArray(new TitlePart[0]);
    }
    public TitlePart[] getAllTitleParts(long parentId) {
    	EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("TitlePart.findAll", TitlePart.class).setParameter("parentId", parentId).getResultList().toArray(new TitlePart[0]);
    }
    public TitlePart getTitlePart(long id) {
    	EntityManager em = emf.createEntityManager();
    	return em.createNamedQuery("TitlePart.find", TitlePart.class).setParameter("id", id).getSingleResult();
    }
    @Transactional
    public TitlePart saveTitlePart(TitlePart tp) {
    	EntityManager em = emf.createEntityManager();

    	//merge will also check @Version number.
    	//see TitlePart implementation
    	return em.merge(tp);
    }
    @Transactional
    public TitlePart addTitlePart(TitlePart tp) {
    	EntityManager em = emf.createEntityManager();
    	em.persist(tp);
    	return tp;
    }
    @Transactional
    public void deleteTitlePart(Long id) {
    	EntityManager em = emf.createEntityManager();
    	TitlePart tpDelete = em.find(TitlePart.class, id);
    	tpDelete.setDeleted(true);
    }
    @PersistenceUnit
    private EntityManagerFactory emf;
//    TODO: Der er sået tvivl om nedenstående er thread safe. Undersøg nærmere.
//    @PersistenceContext
//    private EntityManager em;
}