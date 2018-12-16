package org.collectalot.collectorapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
@SequenceGenerator(name="user-seq", initialValue=20)//TODO kan man slippe af med seq 20 som start
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user-seq")
    private Long id;
	private String name;

    @OneToMany(
            mappedBy = "user", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true
		)
		@PrimaryKeyJoinColumn(name="id", referencedColumnName="user_id")
	private List<TitlePart> titleParts = new ArrayList<>();
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
