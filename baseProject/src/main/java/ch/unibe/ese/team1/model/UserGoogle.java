package ch.unibe.ese.team1.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/** Describes a user on the platform. */
@Entity
@Table(name = "users_google")
public class UserGoogle {
	
	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = true)
	private Gender gender;
	
	@Column(nullable = false)
	private String account;
	
	@Column(nullable = true)
	private String street;
	
	@Column(nullable = true)
	private int zipcode;

	@Column(nullable = true)
	private String city;

	@Column(nullable = false)
	private boolean enabled;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<UserRole> userRoles;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	private UserPicture picture;
	
	@Column(nullable = true)
	@Lob
	private String aboutMe;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Ad> bookmarkedAds;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public UserPicture getPicture() {
		return picture;
	}

	public void setPicture(UserPicture picture) {
		this.picture = picture;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	
	public List<Ad> getBookmarkedAds() {
		return bookmarkedAds;
	}
	
	public void setBookmarkedAds(List<Ad> bookmarkedAds) {
		this.bookmarkedAds = bookmarkedAds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	// equals method is defined to check for id only
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserGoogle other = (UserGoogle) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
