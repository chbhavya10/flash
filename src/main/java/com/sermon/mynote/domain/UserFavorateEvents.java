package com.sermon.mynote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserFavorateEvents")
public class UserFavorateEvents implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int favorateEventId;
	int userId;
	int eventId;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "favorateEventId")
	public int getFavorateEventId() {
		return favorateEventId;
	}

	public void setFavorateEventId(int favorateEventId) {
		this.favorateEventId = favorateEventId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

}
