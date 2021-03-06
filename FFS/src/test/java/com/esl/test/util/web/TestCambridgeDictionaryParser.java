package com.esl.test.util.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.esl.util.web.CambridgeDictionaryParser;

public class TestCambridgeDictionaryParser {

	@Test
	public void testParseBanana() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("banana");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("bənɑːnə",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukb/ukbal/ukballs018.mp3",p.getAudioLink());
	}

	@Test
	public void testParseLemon() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("lemon");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("lemən",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukl/uklei/ukleisu005.mp3",p.getAudioLink());
	}

	@Test
	public void testParseStar() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("star");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("stɑː",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/uks/uksta/ukstand022.mp3",p.getAudioLink());
	}

	@Test
	public void testParsePeak() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("peak");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("piːk",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukp/ukpay/ukpayro024.mp3",p.getAudioLink());
	}

	@Test
	public void testParseFit() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("fit");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("fit",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukf/ukfis/ukfistf003.mp3",p.getAudioLink());
	}

	@Test
	public void testParsePet() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("pet");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("pet",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukp/ukper/ukperv_026.mp3",p.getAudioLink());
	}

	@Test
	public void testParseCat() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("cat");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("kæt",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukc/ukcas/ukcaste011.mp3",p.getAudioLink());
	}

	@Test
	public void testParseFrog() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("frog");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("frɔg",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukf/ukfri/ukfrill022.mp3",p.getAudioLink());
	}

	@Test
	public void testParseCar() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("car");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("kɑː",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukc/ukcap/ukcapit027.mp3",p.getAudioLink());
	}

	@Test
	public void testParseTall() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("tall");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("tɔːl",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukt/uktaj/uktajik024.mp3",p.getAudioLink());
	}

	@Test
	public void testParseFoot() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("foot");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("fut",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukf/ukfol/ukfolks026.mp3",p.getAudioLink());
	}

	@Test
	public void testParseRoom() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("room");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("ruːm",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukr/ukroo/ukrooke003.mp3",p.getAudioLink());
	}

	@Test
	public void testParseLuck() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("luck");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("lʌk",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukl/ukloy/ukloyal030.mp3",p.getAudioLink());
	}

	@Test
	public void testParseBird() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("bird");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("bəːd",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukb/ukbip/ukbipla004.mp3",p.getAudioLink());
	}

	@Test
	public void testParseName() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("name");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("neim",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukm/ukmyt/ukmyth_029.mp3",p.getAudioLink());
	}

	@Test
	public void testParseKite() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("kite");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("kait",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukk/ukkit/ukkit__004.mp3",p.getAudioLink());
	}

	@Test
	public void testParseCow() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("cow");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("kau",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukc/ukcov/ukcover011.mp3",p.getAudioLink());
	}

	@Test
	public void testParseToy() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("toy");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("tɔi",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukt/uktow/uktowni013.mp3",p.getAudioLink());
	}

	@Test
	public void testParseRoad() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("road");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("rəud",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukr/ukrn_/ukrn___004.mp3",p.getAudioLink());
	}

	@Test
	public void testParseTear() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("tear");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("tɛə",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukt/uktea/ukteams003.mp3",p.getAudioLink());
	}

	@Test
	public void testParsePair() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("pair");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("pɛə",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukp/ukpai/ukpaink014.mp3",p.getAudioLink());
	}

	@Test
	public void testParsePoor() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("poor");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("pɔː",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukp/ukpon/ukponti016.mp3",p.getAudioLink());
	}

	@Test
	public void testParseFire() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("fire");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("faiə",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukf/ukfin/ukfink_006.mp3",p.getAudioLink());
	}

	@Test
	public void testParsePower() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("power");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("pauə",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukp/ukpot/ukpotti017.mp3",p.getAudioLink());
	}

	@Test
	public void testParseThin() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("thin");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("θin",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukt/ukthi/ukthick012.mp3",p.getAudioLink());
	}

	@Test
	public void testParseShine() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("shine");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("ʃain",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/uks/ukshi/ukshine001.mp3",p.getAudioLink());
	}

	@Test
	public void testParseHead() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("head");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("hed",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukh/ukhea/ukhead_001.mp3",p.getAudioLink());
	}

	@Test
	public void testParseChurch() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("church");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("tʃəːtʃ",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukc/ukchr/ukchron028.mp3",p.getAudioLink());
	}

	@Test
	public void testParseWild() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("wild");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("waild",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukw/ukwi_/ukwi___023.mp3",p.getAudioLink());
	}

	@Test
	public void testParseSing() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("sing");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("siŋ",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/uks/uksin/uksineq007.mp3",p.getAudioLink());
	}

	@Test
	public void testParseVote() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("vote");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("vəut",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukv/ukvor/ukvorte004.mp3",p.getAudioLink());
	}

	@Test
	public void testParseThey() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("they");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("ðei",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukt/ukthe/ukthera025.mp3",p.getAudioLink());
	}

	@Test
	public void testParseZinc() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("zinc");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("ziŋk",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukz/ukzea/ukzealo022.mp3",p.getAudioLink());
	}

	@Test
	public void testParseMeasure() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("measure");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("meʒə",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukm/ukmea/ukmeant009.mp3",p.getAudioLink());
	}

	@Test
	public void testParseJoke() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("joke");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("dʒəuk",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukj/ukjoc/ukjocul025.mp3",p.getAudioLink());
	}

	@Test
	public void testParseYet() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("yet");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		assertEquals("jet",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/uky/ukyd_/ukyd___027.mp3",p.getAudioLink());
	}

	@Test
	public void testParseBusstop() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("bus-stop");

		assertEquals(false,p.parse());
		assertEquals(false,p.isContentFind());
		assertEquals(null,p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukc/ukcld/ukcld00151.mp3",p.getAudioLink());
	}

	@Test
	public void testParseFebruary() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("february");

		assertTrue(p.parse());
		assertTrue(p.isContentFind());
		//		assertEquals("februəri",p.getIpa());
		assertEquals("http://dictionary.cambridge.org/media/british/uk_pron/u/ukf/ukfea/ukfeast016.mp3",p.getAudioLink());
	}

	@Test
	public void testParseFailGetContent() {
		CambridgeDictionaryParser p = new CambridgeDictionaryParser("asdlkjfwerj");

		assertTrue(!p.parse());
		assertTrue(!p.isContentFind());
	}
}
