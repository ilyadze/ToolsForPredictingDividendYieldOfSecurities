import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {UserCreateDto} from "../models/person/PersonCreateDTO";
import {SecuritiesGetDTO} from "../models/securities/SecuritiesGetDTO";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class HttpClientService {
  constructor(private httpClient: HttpClient) {}

  getSecurities(fromIndex: number, toIndex: number) {
    return this.httpClient.get<SecuritiesGetDTO[]>('http://localhost:8083/securities?from=' + fromIndex + '&to=' + toIndex);
  }

  getSecuritiesLength(): Observable<number> {
    return this.httpClient.get<number>('http://localhost:8083/securities/length');
  }

  public deleteProduct(id: number) {
    return this.httpClient.patch(
      'http://localhost:8080/data/products/delete?ID=' + id.toString(), {}
    );
  }


  getProfile() {
    return this.httpClient.get('http://localhost:8080/data/users/user?email='
      + sessionStorage.getItem('username'));
  }

  // updateProfile(user: User) {
  //   return this.httpClient.patch(
  //     'http://localhost:8080/data/users/update?id='
  //     + sessionStorage.getItem('userId'),
  //     user);
  // }

  deleteAccount() {
    return this.httpClient.patch('http://localhost:8080/data/users/delete?id='
      + sessionStorage.getItem('userId'), {}
    );
  }

  registerUser(user: UserCreateDto) {
    return this.httpClient.post('http://localhost:8083/register', user);
  }

  deleteUser(id: string) {
    return this.httpClient.patch('http://localhost:8080/data/users/delete?id='
      + id, {}
    );
  }

}
