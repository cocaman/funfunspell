package com.esl.svc;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.esl.dao.IGradeDAO;
import com.esl.dao.IMemberDAO;
import com.esl.dao.group.IGroupPracticeResultDAO;
import com.esl.dao.group.IMemberGroupDAO;
import com.esl.dao.group.IMemberGroupMessageDAO;
import com.esl.dao.group.MemberGroupMessageDAO;
import com.esl.exception.BusinessValidationException;
import com.esl.model.Grade;
import com.esl.model.Member;
import com.esl.model.PhoneticQuestion;
import com.esl.model.PracticeResult;
import com.esl.model.TopResult;
import com.esl.model.group.MemberGroup;
import com.esl.model.group.MemberGroupMessage;
import com.esl.service.group.IMemberGroupResultService;
import com.esl.service.group.IMemberGroupService;
import com.esl.service.practice.ITopResultService;
import com.esl.util.PhoneticQuestionUtil;
import com.esl.util.SpringUtil;

public class TestingSvc implements ITestingSvc {
	private static Logger logger = Logger.getLogger("ESL");
	
	public void testing1() {
		ITopResultService t = (ITopResultService) SpringUtil.getContext().getBean("topResultService");
		IGradeDAO g = (IGradeDAO) SpringUtil.getContext().getBean("gradeDAO");
		IMemberDAO mdao = (IMemberDAO) SpringUtil.getContext().getBean("memberDAO");
		IMemberGroupDAO mgd = (IMemberGroupDAO) SpringUtil.getContext().getBean("memberGroupDAO");
		IGroupPracticeResultDAO gprdao = (IGroupPracticeResultDAO) SpringUtil.getContext().getBean("groupPracticeResultDAO");
		
		try
		{	
			gprdao.removeAllGroupResult();
			gprdao.importGroupResult();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void testing2() {
		ITopResultService t = (ITopResultService) SpringUtil.getContext().getBean("topResultService");
		IGradeDAO g = (IGradeDAO) SpringUtil.getContext().getBean("gradeDAO");
		IMemberDAO mdao = (IMemberDAO) SpringUtil.getContext().getBean("memberDAO");
		
		HibernateTransactionManager tm = (HibernateTransactionManager) SpringUtil.getContext().getBean("transactionManager");
		tm.getSessionFactory().openSession();
		
		Grade grade = g.getGradeByTitle("K3");	
		Member member = mdao.getMemberById(new Long(10));
		
		TopResult tr = t.getResultListByMember(TopResult.OrderType.Score, PracticeResult.PHONETICPRACTICE, member);
		System.out.println(tr);		
		for (PracticeResult pr : tr.getTopResults()) {
			System.out.println(pr);
		}
	}
	
	public void testing3() {
		PhoneticQuestion q = new PhoneticQuestion();
		q.setWord("piano");
		PhoneticQuestionUtil pqu = new PhoneticQuestionUtil();
		pqu.findIPA(q);		
	}
	
	public void memberGroupTest() {
		IMemberGroupDAO mgd = (IMemberGroupDAO) SpringUtil.getContext().getBean("memberGroupDAO");
		IMemberGroupMessageDAO mgmd = (IMemberGroupMessageDAO) SpringUtil.getContext().getBean("memberGroupMessageDAO");
		IMemberDAO mdao = (IMemberDAO) SpringUtil.getContext().getBean("memberDAO");
		IGroupPracticeResultDAO gprdao = (IGroupPracticeResultDAO) SpringUtil.getContext().getBean("groupPracticeResultDAO");
		IMemberGroupService mgs = (IMemberGroupService) SpringUtil.getContext().getBean("memberGroupService");
		
		Member member = mdao.getMemberById(new Long(7));		
		MemberGroup group = mgd.getMemberGroupByTitle("a group");
		PracticeResult pr = gprdao.getPracticeResult(member, null, PracticeResult.PHONETICPRACTICE);
				
		try {
			logger.info(mgs.getGroupSummaryByMember(group, member));
		} catch (Exception e) {			
			e.printStackTrace();
			//logger.info("Errorcode:" + e.getErrorCode());
		}
	}
	
	public static void main(String[] args) {
		SpringUtil.initSpring();
		ITestingSvc t = (ITestingSvc) SpringUtil.getContext().getBean("testingSvc");
		t.testing1();
	}
}
