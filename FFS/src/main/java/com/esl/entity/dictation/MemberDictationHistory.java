package com.esl.entity.dictation;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.*;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.esl.model.*;
import com.esl.model.group.MemberGroup;

@Entity
@Table(name = "MEMBER_DICTATION_HISTORY")
public class MemberDictationHistory implements Serializable {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "TOTAL_ATTEMPT")
	private int totalAttempt;
	
	@Column(name = "LAST_MARK")
	private int lastMark;
	
	@Column(name = "LAST_FULLMARK")
	private int lastFullMark;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_PRACTICE_DATE")
	private Date lastPracticeDate;
		
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="DICTATION_ID")
	private Dictation dictation;
	
	@ManyToOne()
	@JoinColumn(name="MEMBER_ID")
	private Member owner;
	
	@OneToMany(mappedBy="dictationHistory", cascade={CascadeType.ALL})
	private List<VocabHistory> vocabHistories;
		
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	// ********************** Constructors ********************** //
	public MemberDictationHistory() {
		totalAttempt = 0;
		createdDate = new Date();
		vocabHistories = new ArrayList<VocabHistory>();
	}
			
	// ********************** Accessor Methods ********************** //
	public Long getId() { return id; }
	private void setId(Long id) { this.id = id; }
	
	public int getTotalAttempt() {return totalAttempt;}
	public void setTotalAttempt(int totalAttempt) {this.totalAttempt = totalAttempt;}
	
	public int getLastMark() {return lastMark;}
	public void setLastMark(int lastMark) {this.lastMark = lastMark;}

	public int getLastFullMark() {return lastFullMark;}
	public void setLastFullMark(int lastFullMark) {this.lastFullMark = lastFullMark;}

	public Date getLastPracticeDate() {return lastPracticeDate;}
	public void setLastPracticeDate(Date lastPracticeDate) {this.lastPracticeDate = lastPracticeDate;}

	public Dictation getDictation() {return dictation;}
	public void setDictation(Dictation dictation) {this.dictation = dictation;}

	public Member getOwner() {return owner;}
	public void setOwner(Member owner) {this.owner = owner;}

	public List<VocabHistory> getVocabHistories() {return vocabHistories;}
	public void setVocabHistories(List<VocabHistory> vocabHistories) {this.vocabHistories = vocabHistories;}
	public void addVocabHistory(VocabHistory vocabHistory) {
		vocabHistories.add(vocabHistory);
		vocabHistory.setDictationHistory(this);
	}

 	public Date getCreatedDate() { return createdDate; }
	public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
				
	// ********************** Common Methods ********************** //
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;		
		if (!(o instanceof MemberDictationHistory)) return false;

		final MemberDictationHistory h = (MemberDictationHistory) o;		
		return this.id.equals(h.getId());
	}
	
	public int hashCode() {
		return id==null ? System.identityHashCode(this) : id.hashCode();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Dictation History ("); sb.append(getId()); sb.append(")");
		return  sb.toString();
	}
}
