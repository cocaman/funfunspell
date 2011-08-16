package org.mintr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="STOCK_QUERY")
public class StockQuery {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	int id;
	
	@Column(name = "STOCK_LIST", nullable = false, length=255)
	String stockList;
	
	public StockQuery() {}
	
	public StockQuery(String list) {
		this.stockList = list;
	}

	public int getId() {return id;}
	public void setId(int id) {this.id = id;}

	public String getStockList() {return stockList;}
	public void setStockList(String stockList) {this.stockList = stockList;}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result
				+ ((stockList == null) ? 0 : stockList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StockQuery other = (StockQuery) obj;
		if (id != other.id)
			return false;
		if (stockList == null) {
			if (other.stockList != null)
				return false;
		} else if (!stockList.equals(other.stockList))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("StockQuery [id=%s, stockList=%s]", id, stockList);
	}
	
}
