export class User {
	private dn!:string;
	private username!:string;
	private fullname!:string;
	public employeeId!:string;

	getDn() {
		return this.dn;
	}

	setDn(dn:string) {
		this.dn = dn;
	}

	getUsername() {
		return this.username;
	}

	setUsername(username:string) {
		this.username = username;
	}

	getFullname() {
		return this.fullname;
	}

	setFullname(fullname:string) {
		this.fullname = fullname;
	}

	getEmployeeId() {
		return this.employeeId;
	}

	setEmployeeId(employeeId:string) {
		this.employeeId = employeeId;
	}
}