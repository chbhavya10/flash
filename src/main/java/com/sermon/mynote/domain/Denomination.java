package com.sermon.mynote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Denomination")
public class Denomination {

	private int denominationId;
	private String denomination;
	private String description;

	public Denomination() {
	}

	public Denomination(String denomination, String description) {
		this.denomination = denomination;
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((denomination == null) ? 0 : denomination.hashCode());
		result = prime * result + denominationId;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Denomination other = (Denomination) obj;
		if (denomination == null) {
			if (other.denomination != null)
				return false;
		} else if (!denomination.equals(other.denomination))
			return false;
		if (denominationId != other.denominationId)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		return true;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "DenominationID")
	public int getDenominationId() {
		return denominationId;
	}

	public void setDenominationId(int denominationId) {
		this.denominationId = denominationId;
	}

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
