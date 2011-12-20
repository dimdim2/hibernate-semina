package hibernate.semina.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class Authority {

	@ManyToOne
	@JoinColumn(name = "ROLE_ID", nullable = false, updatable = false)
	private Role role;

	@Column(name = "AUTHORITY", nullable = false)
	private String authority;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authLevel) {
		this.authority = authLevel;
	}

	public String getAuthString() {
		String authString = null;
		if (authority == null || authority.indexOf("N") > -1 || authority.length() == 0) {
			authString = "None Authority";
		} else {
			if (authority.indexOf("R") > -1) {
				authString = "Read";
			}
			if (authority.indexOf("C") > -1) {
				if (authString != null)
					authString += "|";
				authString += "Create";

			}
			if (authority.indexOf("U") > -1) {
				if (authString != null)
					authString += "|";
				authString += "Update";

			}
			if (authority.indexOf("D") > -1) {
				if (authString != null)
					authString += "|";
				authString += "Delete";
			}
		}
		return authString;
	}
}
