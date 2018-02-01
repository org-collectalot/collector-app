package org.collectalot.collectorapp.db;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

import org.collectalot.collectorapp.model.TitlePart;

@ApplicationScoped
public class TitlePartDBBackend {
/*
 *TODO use @ApplicationScoped hvis du vil referere egne beans 
 */
//    @Inject
//    PersistenceHelper helper;
    public TitlePart[] getAllTitleParts(long parentId) {
    	EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("TitlePart.findAll", TitlePart.class).setParameter("parentId", parentId).getResultList().toArray(new TitlePart[0]);
    }
    public TitlePart getTitlePart(long id) {
    	EntityManager em = emf.createEntityManager();
    	return em.createNamedQuery("TitlePart.find", TitlePart.class).setParameter("id", id).getSingleResult();
    }
    @Transactional
    public void saveTitlePart(TitlePart tp) {
    	EntityManager em = emf.createEntityManager();
    	TitlePart tpPersist = em.find(TitlePart.class, tp.getId());
    	tpPersist.setText(tp.getText());
    	em.persist(tpPersist);
    }
    @PersistenceUnit
    private EntityManagerFactory emf;

}