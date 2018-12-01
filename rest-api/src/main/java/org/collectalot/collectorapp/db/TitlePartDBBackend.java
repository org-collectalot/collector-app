package org.collectalot.collectorapp.db;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.collectalot.collectorapp.model.TitlePart;
import org.collectalot.collectorapp.model.User;

@ApplicationScoped
public class TitlePartDBBackend {
	
    public TitlePart[] getAllTitleParts(User user) {
        return em.createNamedQuery("TitlePart.findAllNoParent", TitlePart.class)
        		  .setParameter("uid", user.getId())
        		  .getResultList().toArray(new TitlePart[0]);
    }
    public TitlePart[] getAllTitleParts(User user, long parentId) {
        return em.createNamedQuery("TitlePart.findAll", TitlePart.class)
        		  .setParameter("parentId", parentId)
        		  .setParameter("uid", user.getId())
        		  .getResultList().toArray(new TitlePart[0]);
    }
    public TitlePart getTitlePart(User user, long id) {
    	List<TitlePart> titleParts = em.createNamedQuery("TitlePart.find", TitlePart.class)
    		   .setParameter("id", id)
    		   .setParameter("uid", user.getId())
    		   .getResultList();
    	if (titleParts.size() == 1) {
    		return titleParts.get(0);
    	} else if(titleParts.size() == 0) { 
    		return null;
        } else {
        	throw new IllegalStateException("Too many items found.");
        }
    }
    @Transactional
    public TitlePart saveTitlePart(User user, TitlePart tp) {
    	tp.setUser(user);
    	//merge will also check @Version number.
    	//see TitlePart implementation
    	return em.merge(tp);
    }
    @Transactional
    public TitlePart addTitlePart(User user, TitlePart tp) {
    	User userLoggedOn = em.find(User.class, user);
    	tp.setUser(userLoggedOn);
    	em.persist(tp);
    	return tp;
    }
    @Transactional
    public void deleteTitlePart(User user, Long id) {
    	TitlePart tpDelete = em.find(TitlePart.class, id);
    	if(user.getId() != tpDelete.getUser().getId()) {
    		throw new IllegalAccessError("User not allowed to access this object");
    	}
    	tpDelete.setDeleted(true);
    }
    @PersistenceContext(unitName = "MyPU")
    private EntityManager em;
}