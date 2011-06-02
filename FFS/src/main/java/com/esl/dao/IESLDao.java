package com.esl.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface IESLDao<T> {
	public Object attachSession(Object o);
	public void flush();
	public void persist(T entity);
	public void persistAll(Collection<T> entities);
	public void refresh(T entity);
	public T merge(T entity);
	public void delete(T entity);
	public void deleteAll(Collection<T> entities);
	public T load(Serializable id);
	public List<T> loadAll();
}
