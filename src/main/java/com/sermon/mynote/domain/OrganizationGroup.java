package com.sermon.mynote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OrganizationGroup")
public class OrganizationGroup implements Serializable {

 /**
  * 
  */
 private static final long serialVersionUID = 1L;

 private int organizationGroupId;
 private String groupName;
 private String groupDescription;

 public OrganizationGroup() {

 }

 public OrganizationGroup(int organizationGroupId, String groupName, String groupDescription) {
  this.organizationGroupId = organizationGroupId;
  this.groupName = groupName;
  this.groupDescription = groupDescription;
 }

 @Id
 @GeneratedValue(strategy = IDENTITY)
 @Column(name = "OrganizationGroupId")
 public int getOrganizationGroupId() {
  return organizationGroupId;
 }

 public void setOrganizationGroupId(int organizationGroupId) {
  this.organizationGroupId = organizationGroupId;
 }

 public String getGroupName() {
  return groupName;
 }

 public void setGroupName(String groupName) {
  this.groupName = groupName;
 }

 public String getGroupDescription() {
  return groupDescription;
 }

 public void setGroupDescription(String groupDescription) {
  this.groupDescription = groupDescription;
 }

}