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

import com.esl.model.group.MemberGroup;

@Entity
@Table(name = "DICTATION_VOCAB")
public class Vocab implements Serializable {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
		
	@Column(name = "WORD")
	private String word;
	
	@Column(name = "TOTAL_CORRECT")
	private int totalCorrect;
	
	@Column(name = "TOTAL_WRONG")
	private int totalWrong;
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="DICTATION_ID")
	private Dictation dictation;
			
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	// ********************** Constructors ********************** //
	public Vocab() {
		totalWrong = 0;
		totalCorrect = 0;
		createdDate = new Date();
	}
	
	public Vocab(String word) {
		this();
		this.word = word;		
	}
		
	// ********************** Accessor Methods ********************** //
	public Long getId() { return id; }
	private void setId(Long id) { this.id = id; }
	
	public String getWord() {return word;}
	public void setWord(String word) {this.word = word;}

	public int getTotalCorrect() {	return totalCorrect;}
	public void setTotalCorrect(int totalCorrect) {	this.totalCorrect = totalCorrect;}

	public int getTotalWrong() {return totalWrong;}
	public void setTotalWrong(int totalWrong) {	this.totalWrong = totalWrong;}

	public Dictation getDictation() {return dictation;}
	public void setDictation(Dictation dictation) {	this.dictation = dictation;	}

	public Date getCreatedDate() { return createdDate; }
	public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
	
	public int getCorrectPercent() {
		if (totalCorrect + totalWrong == 0) return 0;
		return (int) totalCorrect * 100 / (totalCorrect + totalWrong);		
	}
	public int getWrongPercent() {
		if (totalCorrect + totalWrong == 0) return 0;
		return (int) totalWrong * 100 / (totalCorrect + totalWrong);
	}
				
	// ********************** Common Methods ********************** //
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;		
		if (!(o instanceof Vocab)) return false;

		final Vocab v = (Vocab) o;		
		return this.id.equals(v.getId());
	}
	
	public int hashCode() {
		return id==null ? System.identityHashCode(this) : id.hashCode();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Vocab ("); sb.append(getId()); sb.append("): ");
		sb.append("Word["); sb.append(getWord()); sb.append("] ");		
		return  sb.toString();				
	}
}
