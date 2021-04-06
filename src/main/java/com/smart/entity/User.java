package com.smart.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="user")

public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
private int id;
	@NotBlank(message="Name Shoudnot Be Blanked")
private String name;
	@Column(unique=true)
private String Email;
private String password;
private String role;
private boolean enabled;
private String imageUrl;
private String about;
@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="user")
private List<Contact>contacts=new ArrayList<>();
public List<Contact> getContacts() {
	return contacts;
}
public void setContacts(List<Contact> contacts) {
	this.contacts = contacts;
}
@Override
public String toString() {
	return "User [id=" + id + ", name=" + name + ", Email=" + Email + ", password=" + password + ", role=" + role
			+ ", enabled=" + enabled + ", imageUrl=" + imageUrl + ", about=" + about + "]";
}
public User() {
	super();
}
public User(int id, String name, String email, String password, String role, boolean enabled, String imageUrl,
		String about) {
	super();
	this.id = id;
	this.name = name;
	Email = email;
	this.password = password;
	this.role = role;
	this.enabled = enabled;
	this.imageUrl = imageUrl;
	this.about = about;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return Email;
}
public void setEmail(String email) {
	Email = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public boolean isEnabled() {
	return enabled;
}
public void setEnabled(boolean enabled) {
	this.enabled = enabled;
}
public String getImageUrl() {
	return imageUrl;
}
public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
}
public String getAbout() {
	return about;
}
public void setAbout(String about) {
	this.about = about;
}

}
