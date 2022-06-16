package com.devsuperior.dsmovie.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "tb_score")
public class Score {

	@EmbeddedId
	private ScorePK id = new ScorePK();
	private Double value;
	
	public Score() {
		
	}
	
	public Score(ScorePK id, Double value) {
		super();
		this.id = id;
		this.value = value;
	}

	@Override
	public String toString() {
		return "Score{" +
				"id=" + id +
				", value=" + value +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Score)) return false;
		Score score = (Score) o;
		return id.equals(score.id) && value.equals(score.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, value);
	}

	public void setMovie(Movie movie) {
		id.setMovie(movie);
	}
	
	public void setUser(User user) {
		id.setUser(user);
	}

	public ScorePK getId() {
		return id;
	}

	public void setId(ScorePK id) {
		this.id = id;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
	
	
}
