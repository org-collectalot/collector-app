package org.collectalot.collectorapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "TITLE_PART")
@NamedQueries({
        @NamedQuery(name = "TitlePart.findAllNoParent", query = "SELECT tp FROM TitlePart tp WHERE tp.parentId is null AND tp.deleted = FALSE"),
        @NamedQuery(name = "TitlePart.findAll", query = "SELECT tp FROM TitlePart tp WHERE tp.parentId = :parentId AND tp.deleted = FALSE"),
        @NamedQuery(name = "TitlePart.find", query = "SELECT tp FROM TitlePart tp WHERE tp.id = :id AND tp.deleted = FALSE")
})
@XmlRootElement
@SequenceGenerator(name="seq", initialValue=20)//TODO kan man slippe af med seq 20 som start
public class TitlePart implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    private Long id;
    
    @Column(name="PARENT_ID")
    private Long parentId;

    @Column(length = 100)
	private String text;

    @Version
    private int version;
    
    private boolean deleted;
    
    public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
