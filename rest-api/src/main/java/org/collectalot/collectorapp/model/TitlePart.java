package org.collectalot.collectorapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "TITLE_PART")
@NamedQueries({
        @NamedQuery(name = "TitlePart.findAll", query = "SELECT tp FROM TitlePart tp WHERE tp.parentId = :parentId"),
        @NamedQuery(name = "TitlePart.find", query = "SELECT tp FROM TitlePart tp WHERE tp.id = :id")
})
@XmlRootElement
public class TitlePart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="PARENT_ID")
    private Long parentId;

    @Column(length = 100)
	private String text;

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
