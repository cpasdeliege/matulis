export class Authentication {
	private token!:string;
	private username!:string;

	constructor(
		token:string,
		username:string
	) {
		this.setToken(token);
		this.setUsername(username);
	}

	getToken() {
		return this.token;
	}

	setToken(token:string){
		this.token = token;
	}

	getUsername() {
		return this.username;
	}

	setUsername(username:string) {
		this.username = username;
	}
}