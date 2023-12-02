import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {UserCreateDto} from "../models/person/PersonCreateDTO";
import {SecuritiesGetDTO} from "../models/securities/SecuritiesGetDTO";
import {Observable} from "rxjs";
import {SecuritiesInfoDTO} from "../models/securities/SecuritiesInfoDTO";
import {SecuritiesSearchDTO} from "../models/securities/SecuritiesSearchDTO";


@Injectable({
  providedIn: 'root'
})
export class HttpClientService {

  private base_url = 'http://localhost:8083/securities'
  constructor(private httpClient: HttpClient) {}

  getSecurities(fromIndex: number, toIndex: number) {
    return this.httpClient.get<SecuritiesGetDTO[]>(this.base_url + '?from=' + fromIndex + '&to=' + toIndex);
  }

  getSecuritiesInfo(symbol: string) {
    return this.httpClient.get<SecuritiesInfoDTO>(this.base_url + '/' + symbol);
  }

  search(query: string): Observable<any[]> {
    console.log("Search");
    return this.httpClient.get<SecuritiesSearchDTO[]>(`${this.base_url}/search?query=${query}`);
  }

  getSecuritiesLength(): Observable<number> {
    return this.httpClient.get<number>(this.base_url + '/length');
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
