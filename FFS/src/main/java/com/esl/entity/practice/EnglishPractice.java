package com.esl.entity.practice;

import java.io.Serializable;

import javax.persistence.*;

import com.esl.entity.practice.qa.EnglishQuestions;
import com.esl.web.model.PasswordRequire;

@Entity
@DiscriminatorValue("ENGLISH_PRACTICE")
public class EnglishPractice extends Practice implements Serializable, PasswordRequire {

	public enum Type {
		Tense, Comprehension, SentenceStructure, ProofReading, Articles, Preposition, Conjunction, Pronoun, Adjective
	}

	@Column(name = "TYPE")
	@Enumerated(EnumType.STRING)
	private Type englishPracticeType;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "QUESTIONS_CONTENT")
	private EnglishQuestions questionContent;

	// ********************** Constructors ********************** //
	public EnglishPractice() {}

	// ********************** Accessor Methods ********************** //

	public Type getEnglishPracticeType() {return englishPracticeType;}
	public void setEnglishPracticeType(Type englishPracticeType) {this.englishPracticeType = englishPracticeType;}

	public EnglishQuestions getQuestionContent() {return questionContent;}
	public void setQuestionContent(EnglishQuestions questionContent) {this.questionContent = questionContent;}

	// ********************** Common Methods ********************** //

	@Override
	public String toString() {
		return String.format("EnglishPractice (%s) [type=%s, title=%s, createdDate=%s]", getId(), englishPracticeType, getTitle(), getCreatedDate());
	}
}
