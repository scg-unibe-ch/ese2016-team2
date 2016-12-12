package ch.unibe.ese.team1.controller.pojos.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotBlank;

/** This form is used when a user want to register a premium account. */
public class RegisterForm {
	
	@NotBlank(message = "Required")
	private String username;
	
	@NotBlank(message = "Required")
	private String street;
	
	@Pattern(regexp = "^[0-9]{4} - [-;.\\w\\s\\u00C0-\\u00FF]*", message = "Please pick a city from the list")
	private String city;
	
	@NotNull(message = "Required")
	private String account;

	/**
	* 	Getter and setter methods of
	* 	the attributes of premium account.
	*/
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = "Premium";
	}
}
