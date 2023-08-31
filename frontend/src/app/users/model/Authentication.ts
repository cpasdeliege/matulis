import { Type } from "class-transformer";
import { User } from "./User";

export class Authentication {
	private token!:string;

	@Type(() => User)
	private user!:User;

	getToken() {
		return this.token;
	}

	setToken(token:string){
		this.token = token;
	}

	getUser() {
		return this.user;
	}

	setUser(user:User) {
		this.user = user;
	}
}