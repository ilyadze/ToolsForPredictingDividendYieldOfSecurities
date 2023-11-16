import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  constructor(private httpClient: HttpClient) {}



  authenticate(email: any, password:any) {
    console.log({email , password });
    return this.httpClient
      .post<any>('http://localhost:8083/login', {email , password })
      .pipe(
        map(userData => {
          sessionStorage.setItem('username', email);
          let tokenStr = userData.token;
          sessionStorage.setItem('token', tokenStr);
          sessionStorage.setItem('userId', userData.userId);
          return userData;
        })
      );
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem('username');
    console.log(!(user === null));
    return !(user === null);
  }

  logOut() {
    sessionStorage.removeItem('username');
    sessionStorage.removeItem('userId');
  }
}
