export class User {
	private dn!:string;
	private username!:string;
	private fullname!:string;

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
}