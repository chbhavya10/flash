package com.sermon.mynote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the tx_stripe_payments database table.
 * 
 */
@Entity
@Table(name="donations")
@NamedQuery(name="Donation.findAll", query="SELECT t FROM Donation t")
public class Donation implements Serializable {
	private static final long serialVersionUID = 1L;

	private String balanceTxnId;

	private int chargeAmt;

	private String chargeId;
	
	private String destinationAcctId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	private String status;
	
	private Long txId;
	
	public void setTxId(Long txId) {
		this.txId = txId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "txId")
	public Long getTxId() {
		return this.txId;
	}


	public Donation() {
		
	}

	public Donation(String balanceTxnId, int chargeAmt, String chargeId, Date createDate, String status,
			Long txId) {
		super();
		this.balanceTxnId = balanceTxnId;
		this.chargeAmt = chargeAmt;
		this.chargeId = chargeId;
		this.createDate = createDate;
		this.status = status;
		this.txId = txId;
	}

	public String getBalanceTxnId() {
		return this.balanceTxnId;
	}

	public void setBalanceTxnId(String balanceTxnId) {
		this.balanceTxnId = balanceTxnId;
	}

	public int getChargeAmt() {
		return this.chargeAmt;
	}

	public void setChargeAmt(int chargeAmt) {
		this.chargeAmt = chargeAmt;
	}

	public String getChargeId() {
		return this.chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDestinationAcctId() {
		return destinationAcctId;
	}

	public void setDestinationAcctId(String destinationAcctId) {
		this.destinationAcctId = destinationAcctId;
	}

 
 
}