package com.sermon.mynote.domain;

import java.io.Serializable;

public class LimitParameters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int start;
	private int length;

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
