package org.mintr.repository;

import org.mintr.entity.StockQuery;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface StockQueryRepository extends GraphRepository<StockQuery> {

}
