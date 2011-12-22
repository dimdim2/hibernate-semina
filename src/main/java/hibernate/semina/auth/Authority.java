package hibernate.semina.auth;

public class Authority {

	private String authLevel;
	private boolean isRead;
	private boolean isCreate;
	private boolean isUpdate;
	private boolean isDelete;

	public Authority(String authLevel) {
		setAuthLevel(authLevel);
	}

	public String getAuthLevel() {
		return authLevel;
	}

	public void setAuthLevel(String authLevel) {
		this.authLevel = authLevel;

		isRead = isRead || (authLevel.indexOf("R") != -1);
		isCreate = isCreate || (authLevel.indexOf("C") != -1);
		isUpdate = isUpdate || (authLevel.indexOf("U") != -1);
		isDelete = isDelete || (authLevel.indexOf("D") != -1);
	}

	public boolean isRead() {
		return isRead;
	}

	public boolean isCreate() {
		return isCreate;
	}

	public boolean isUpdate() {
		return isUpdate;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public boolean hasAuthority() {
		return false;
	}
}
