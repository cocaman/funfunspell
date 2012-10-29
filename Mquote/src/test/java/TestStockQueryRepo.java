import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mintr.dao.StockQueryDao;
import org.mintr.entity.StockQuery;
import org.mintr.repository.StockQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:test_applicationContext.xml"})
public class TestStockQueryRepo {
	Logger log = LoggerFactory.getLogger(TestStockQueryRepo.class);
	StockQuery list1 = new StockQuery("1234");
	StockQuery list2 = new StockQuery("list2");
	
	@Autowired
	private StockQueryRepository stockQueryRepo;
	
	@Before
	public void before() {	
		list1.setId(1);
		stockQueryRepo.save(list1);
	}
	
	@After
	public void after() {
		stockQueryRepo.deleteAll();
	}
	
	@Test @Transactional 
	public void testGetOne() {
		StockQuery q = stockQueryRepo.findByPropertyValue("id", 1);
			
		assertTrue(list1.getStockList().equals(q.getStockList()));
	}
	
	
}
