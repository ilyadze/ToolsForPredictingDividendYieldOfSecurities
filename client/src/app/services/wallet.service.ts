import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {WalletSecuritiesAddDTO} from "../models/wallet/WalletSecuritiesAddDTO";
import {WalletSecurityGetDTO} from "../models/wallet/WalletSecurityGetDTO";

@Injectable({
  providedIn: 'root'
})
export class WalletService {
  base_url: string = 'http://localhost:8083/person/'

  constructor(private httpClient: HttpClient) { }

  getSecurities() {
    return this.httpClient.get<WalletSecurityGetDTO[]>(this.base_url + sessionStorage.getItem('email') + '/securities');
  }

  addSecurities(securities: WalletSecuritiesAddDTO) {
    this.httpClient.post(this.base_url + sessionStorage.getItem('email') + '/securities', securities);
  }
}
