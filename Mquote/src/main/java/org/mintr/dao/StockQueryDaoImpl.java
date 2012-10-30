package org.mintr.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.mintr.entity.StockQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//@Repository
//@Transactional
public class StockQueryDaoImpl implements StockQueryDao {

	@PersistenceContext
    private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StockQuery> getAll() {
		return em.createQuery("from StockQuery").getResultList();
	}

	@Override
	public void persist(StockQuery entity) {
		em.persist(entity);
	}

	@Override
	public int deleteAll() {
		return em.createQuery("delete from StockQuery").executeUpdate();
		
	}

}
