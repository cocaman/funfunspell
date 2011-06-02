package com.esl.entity.practice;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

import com.esl.enumeration.AgeGroup;
import com.esl.model.Member;
import com.esl.model.group.MemberGroup;
import com.esl.web.model.PasswordRequire;

@Entity
@Table(name = "PRACTICE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DISC", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("PRACTICE")
public class Practice implements Serializable, PasswordRequire {

	public static final int SHORT_TITLE_LENGHT = 30;
	public static final String SEPARATOR = ",";

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "SUITABLE_MIN_AGE")
	private int suitableMinAge;

	@Column(name = "SUITABLE_MAX_AGE")
	private int suitableMaxAge;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "TAGS")
	private String tags;

	@Column(name = "IS_PUBLIC")
	private boolean isPublic;

	@Column(name = "PASSWORD")
	private String password;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MODIFY_DATE")
	private Date lastModifyDate;

	@ManyToOne()
	@JoinColumn(name="MEMBER_ID")
	private Member publisher;

	@ManyToMany()
	@JoinTable(name="PRACTICE_MEMBERGROUP", joinColumns=@JoinColumn(name="PRACTICE_ID"), inverseJoinColumns=@JoinColumn(name="MEMBERGROUP_ID"))
	private List<MemberGroup> accessibleGroups;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	// ********************** Constructors ********************** //
	public Practice() {
		suitableMinAge = -1;
		createdDate = new Date();
		isPublic = true;
		createdDate = new Date();
		lastModifyDate = new Date();
		accessibleGroups = new ArrayList<MemberGroup>();
	}

	public Practice(String title) {
		this();
		this.title = title;
	}

	// ********************** Accessor Methods ********************** //
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;	}

	public String getShortTitle() {
		if (title.length() > SHORT_TITLE_LENGHT) return title.substring(0, SHORT_TITLE_LENGHT);
		else return title;
	}

	public int getSuitableMinAge() {return suitableMinAge;}
	public void setSuitableMinAge(int suitableMinAge) {this.suitableMinAge = suitableMinAge;}

	public int getSuitableMaxAge() {return suitableMaxAge;}
	public void setSuitableMaxAge(int suitableMaxAge) {this.suitableMaxAge = suitableMaxAge;}

	public List<Integer> getSuitableAgeGroups() {
		List<Integer> ags = new ArrayList<Integer>();

		// return any if minAge is not set
		if (suitableMinAge < 1) { ags.add(AgeGroup.AgeAny.ordinal()); return ags; }

		for (AgeGroup a : AgeGroup.values()) {
			if (a.minAge >= suitableMinAge && a.minAge <= suitableMaxAge) {
				ags.add(a.ordinal());
				continue;
			}
			if (a.maxAge >= suitableMinAge && a.maxAge <= suitableMaxAge) {
				ags.add(a.ordinal());
				continue;
			}
		}
		return ags;
	}
	public void setSuitableAgeGroups(List<String> suitableAgeGroups) {
		if (suitableAgeGroups != null && suitableAgeGroups.size() > 0) {
			suitableMinAge = AgeGroup.values()[Integer.parseInt(suitableAgeGroups.get(0))].minAge;
			suitableMaxAge = AgeGroup.values()[Integer.parseInt(suitableAgeGroups.get(0))].maxAge;
		}
		for (String i : suitableAgeGroups) {
			AgeGroup a = AgeGroup.values()[Integer.parseInt(i)];
			if (a == AgeGroup.AgeAny) {
				suitableMinAge = suitableMaxAge = -1;
				return;
			}
			if (suitableMinAge > a.minAge) suitableMinAge = a.minAge;
			if (suitableMaxAge < a.maxAge) suitableMaxAge = a.maxAge;
		}
	}
	public String getSuitableAge() {
		if (suitableMinAge < 1) return AgeGroup.AgeAny.toString();
		return suitableMinAge + " - " + suitableMaxAge;
	}

	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}

	public String getTags() {return tags;}
	public void setTags(String tags) {this.tags = tags.toLowerCase();}

	public boolean isPublic() {return isPublic;}
	public void setPublic(boolean isPublic) {this.isPublic = isPublic;}

	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	public boolean isRequirePassword() { return (password != null && !password.equals("")); }

	public Date getLastModifyDate() {return lastModifyDate;}
	public void setLastModifyDate(Date lastModifyDate) {this.lastModifyDate = lastModifyDate;}

	public Member getCreator() {return publisher;}
	public void setCreator(Member creator) {this.publisher = creator;}

	public List<MemberGroup> getAccessibleGroups() {return accessibleGroups;}
	public void setAccessibleGroups(List<MemberGroup> accessibleGroups) {this.accessibleGroups = accessibleGroups;}
	public void addAccessibleGroup(MemberGroup group) {
		accessibleGroups.add(group);
	}

	public Date getCreatedDate() { return createdDate; }
	public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }

	// ********************** Common Methods ********************** //
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		if (!(o instanceof Practice)) return false;

		final Practice h = (Practice) o;
		return this.id.equals(h.getId());
	}

	@Override
	public int hashCode() {
		return id==null ? System.identityHashCode(this) : id.hashCode();
	}

	@Override
	public String toString() {
		return String.format("Practice (%s) [title=%s, createdDate=%s]", id, createdDate, title);
	}

}
