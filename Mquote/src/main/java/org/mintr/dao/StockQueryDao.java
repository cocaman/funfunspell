package org.mintr.dao;

import java.util.List;

import org.mintr.entity.StockQuery;

public interface StockQueryDao {
	public List<StockQuery> getAll();
	public void persist(StockQuery entity);
	public int deleteAll();
}
