package org.collectalot.collectorapp.db;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
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
	/**
	 * Save a titlePart. If it already exists, update it.
	 * If it doesn't exist, try to create it.
	 * 
	 * @param user The user logged on to the system
	 * @param tp The title part to save
	 * @return The saved titlepart with updated values.
	 */
    @Transactional
    public TitlePart saveTitlePart(User user, TitlePart tp) {
    	tp.setUser(user);
    	//merge will also check @Version number.
		//see TitlePart implementation
		try {
			List<TitlePart> titleParts = em.createNamedQuery("TitlePart.find", TitlePart.class)
			                               .setParameter("id", tp.getId())
			                               .setParameter("uid", user.getId())
			                               .getResultList();
			if (titleParts.size() == 1) {
				if(titleParts.get(0).isDeleted()) {
					throw new IllegalArgumentException("object already deleted. No updates allowed.");
				}
				return em.merge(tp);
			} else if(titleParts.size() == 0) { 
				return addTitlePart(user, tp);
			} else {
				throw new IllegalStateException("Too many items found.");
			}
		} catch(OptimisticLockException e) {
			throw new IllegalArgumentException("Version number " + tp.getVersion() + " doesn't correspond to database.");
		}
	}
	/**
	 * Try to create a new title-part.
	 * 
	 * //TODO fail if title-part path already exists?
	 * @param user
	 * @param tp
	 * @return
	 */
    @Transactional
    public TitlePart addTitlePart(User user, TitlePart tp) {
		User userLoggedOn = em.find(User.class, user.getId());
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