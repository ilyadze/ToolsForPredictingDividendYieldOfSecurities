import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {PersonGetDTO} from "../models/person/PersonGetDTO";

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  base_url:string = "http://localhost:8083/person";

  constructor(private httpClient: HttpClient) { }

  getPerson() {
    return this.httpClient.get<PersonGetDTO>(this.base_url + '/' + localStorage.getItem('email'));
  }
}
