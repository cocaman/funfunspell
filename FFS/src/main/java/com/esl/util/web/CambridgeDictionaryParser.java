package com.esl.util.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.esl.model.practice.PhoneticSymbols;
import com.esl.util.SourceChecker;
import com.esl.util.WebUtil;

public class CambridgeDictionaryParser implements SourceChecker, DictionaryParser {
	private Logger logger = LoggerFactory.getLogger("ESL");

	final String URLPrefix = "http://dictionary.cambridge.org/dictionary/british/";
	final String IPAPrefix = "<span class=\"prons \"><span class=\"pron \">/";
	final String audioPrefix = "playSoundFromFlash('/media/";
	final String audioSuffix = ".mp3";
	final String audioURLPrefix = "http://dictionary.cambridge.org/media/";
	final int IPAPositionLimit = 300;

	String ipa;
	String audioLink;
	String query;			// input word

	// ------------------------ getter / setter --------------------- //
	public CambridgeDictionaryParser(String query) {
		this.query = query.toLowerCase();
	}

	public boolean isContentFind() {
		return StringUtils.hasText(ipa) && StringUtils.hasText(audioLink);
	}

	public String getIpa() {return this.ipa;}
	public String getAudioLink() {return this.audioLink;}

	// ------------------------ function --------------------- //
	public boolean parse() {
		BufferedReader br;
		try {
			br = WebUtil.getReaderFromURL(concatURL());
		} catch (IOException e1) {
			try {
				br = WebUtil.getReaderFromURL(concatURL2());
			} catch (IOException e) {
				logger.warn("cannot get reader when query [{}]", query);
				return false;
			}
		}

		try {
			String line = br.readLine();
			int pos;
			while (line != null) {

				pos = extractAudioLink(line);
				if (pos >= 0) {
					extractIPA(line, pos);
					break;
				}

				line = br.readLine();

			}
		} catch (Exception e) {
			logger.warn("cannot get reader when query [{}]", query);
		}

		return isContentFind();
	}

	private void extractIPA(String line, int lastPos) {
		int startPos = line.indexOf(IPAPrefix, lastPos);
		int endPos = line.indexOf("/", startPos + IPAPrefix.length());
		int endPosOfSmallThan = line.indexOf("<", startPos + IPAPrefix.length());
		if (endPosOfSmallThan > 0 && endPosOfSmallThan < endPos) {
			endPos = endPosOfSmallThan;
		}

		// only set the IPA if the ipa found within limit
		if (startPos > 0 && startPos < (lastPos + IPAPositionLimit)) {
			ipa = PhoneticSymbols.filterIPA(PhoneticSymbols.convertGoogleIPA(line.substring(startPos + IPAPrefix.length(), endPos)));
		}
	}

	private int extractAudioLink(String line) {
		int startPos = line.indexOf(audioPrefix);
		if (startPos < 0) return startPos;

		// audio line find
		int endPos = line.indexOf(audioSuffix, startPos);
		audioLink = audioURLPrefix + line.substring(startPos + audioPrefix.length(), endPos) + audioSuffix;
		return endPos;
	}

	private String concatURL() {
		return URLPrefix + query;
	}

	private String concatURL2() {
		return URLPrefix + query + "_1?q=" + query;
	}

	@Override
	public String getSourceLink() {		
		return concatURL() + " or " + concatURL2();
	}

	@Override
	public String getParsedContent() {
		return MessageFormat.format("[ipa:{0}],[audioLink:{1}]", ipa, audioLink);
	}
}
