import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { map } from 'rxjs/operators';
import {Observable} from "rxjs";
import {UserCreateDto} from "../models/person/PersonCreateDTO";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  constructor(private httpClient: HttpClient) {}

    base_url = 'http://localhost:8083';

  authenticate(email: any, password:any) {
    return this.httpClient
      .post<any>(this.base_url  + '/login', {email , password })
      .pipe(
        map(userData => {
          sessionStorage.setItem('email', email);
          let tokenStr = userData.token;
          sessionStorage.setItem('token', tokenStr);
          sessionStorage.setItem('userId', userData.userId);
          return userData;
        })
      );
  }


  registerUser(user: UserCreateDto) {
      return this.httpClient.post('http://localhost:8083/register', user);
  }

  activateAccount(activationCode: string): Observable<any> {
      return this.httpClient.get(`${this.base_url}/activate/${activationCode}`);
  }


  isUserLoggedIn() {
    let user = sessionStorage.getItem('email');
    return !(user === null);
  }

  logOut() {
    sessionStorage.removeItem('username');
    sessionStorage.removeItem('userId');
  }
}
