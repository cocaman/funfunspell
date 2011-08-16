import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mintr.dao.StockQueryDao;
import org.mintr.entity.StockQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:test_applicationContext.xml"})
public class TestStockQueryDao {
	Logger log = LoggerFactory.getLogger(TestStockQueryDao.class);
	StockQuery list1 = new StockQuery("1234");
	StockQuery list2 = new StockQuery("list2");
	
	@Autowired
	StockQueryDao stockQueryDao;
	
	@Before
	public void before() {		
		stockQueryDao.persist(list1);
		stockQueryDao.persist(list2);
	}
	
	@After
	public void after() {
		stockQueryDao.deleteAll();
	}
	
	@Test
	public void testGetAll() {
		log.info("<<<<<<<<<<<<<<testGetAll>>>>>>>>>>>>>");
		List<StockQuery> result = stockQueryDao.getAll();
		
		assertEquals(2, result.size());
		assertTrue(result.contains(list1));
		assertTrue(result.contains(list2));
	}
	
	@Test	
	@Rollback(true)
	public void testPersistOne() {
		log.info("<<<<<<<<<<<<<<testPersistOne>>>>>>>>>>>>>");
		StockQuery q1 = new StockQuery("q1");
		stockQueryDao.persist(q1);
		List<StockQuery> result = stockQueryDao.getAll();
		assertEquals(3, result.size());
		assertTrue(result.contains(q1));
	}
	
}
