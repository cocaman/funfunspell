package com.esl.test.dao;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.esl.dao.dictation.IDictationDAO;
import com.esl.entity.dictation.Dictation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:test-dao.xml"})
public class TestDictationDao {
	Logger log = LoggerFactory.getLogger(TestDictationDao.class);
	Dictation dic1, dic2;
	
	@Autowired
	public IDictationDAO dictationDao;
	
	public TestDictationDao() {
		dic1 = new Dictation("dic1");
		dic2 = new Dictation("dic2");
	}
	
	@Before
	public void before() {		
		dictationDao.persist(dic1);
		dictationDao.persist(dic2);
	}
	
	@After
	public void after() {
		dictationDao.delete(dic1);
		dictationDao.delete(dic2);
	}
	
	@Test
	public void testGetAll() {
		log.info("<<<<<<<<<<<<<<testGetAll>>>>>>>>>>>>>");
		List<Dictation> result = dictationDao.loadAll();
		
		assertEquals(2, result.size());
		assertTrue(result.contains(dic1));
		assertTrue(result.contains(dic2));
	}
	
	@Test	
	@Rollback(true)
	public void testPersistOne() {
		log.info("<<<<<<<<<<<<<<testPersistOne>>>>>>>>>>>>>");
		Dictation d1 = new Dictation("d1");
		dictationDao.persist(d1);
		List<Dictation> result = dictationDao.loadAll();
		assertEquals(3, result.size());
		assertTrue(result.contains(d1));
	}
	
}
