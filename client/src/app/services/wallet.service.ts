import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {WalletSecuritiesAddDTO} from "../models/wallet/WalletSecuritiesAddDTO";
import {WalletSecurityGetDTO} from "../models/wallet/WalletSecurityGetDTO";
import {formatDate} from "@angular/common";
import {WalletInfoDTO} from "../models/wallet/WalletInfoDTO";
import {Filters} from "../models/securities/Filters";
import {Observable} from "rxjs";
import {SecurityDividendGetDTO} from "../models/charts/SecurityDividendGetDTO";

@Injectable({
  providedIn: 'root'
})
export class WalletService {
  base_url: string = 'http://localhost:8083/person/'

  constructor(private httpClient: HttpClient) { }

  getSecurities() {
    return this.httpClient.get<WalletSecurityGetDTO[]>(this.base_url + localStorage.getItem('email') + '/securities');
  }

  deleteSecurity(symbol: string) {
    return this.httpClient.delete(this.base_url + localStorage.getItem('email') + '/securities/' + symbol);
  }

  getWalletInfo() {
    return this.httpClient.get<WalletInfoDTO>(this.base_url + localStorage.getItem('email') + '/securities/wallet');
  }

  getSecuritiesDividends(symbol:string): Observable<SecurityDividendGetDTO[]> {
    return this.httpClient.get<SecurityDividendGetDTO[]>(this.base_url + localStorage.getItem('email') + '/securities/dividends');
  }

  addSecurities(securities: WalletSecuritiesAddDTO) {
    securities.dateOfPurchase = this.formatDate(new Date());
    this.httpClient.post(`${this.base_url}${localStorage.getItem('email')}/securities`, securities).subscribe(response => {
      console.log('Ценная бумага успешно добавлена в кошелек', response);
      // Можете выполнить дополнительные действия после успешного добавления
    }, error => {
      console.error('Произошла ошибка при добавлении ценной бумаги в кошелек', error);
    });
  }

  private formatDate(date: Date): string {
    // Пример форматирования даты в строку "YYYY-MM-DD"
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const day = ('0' + date.getDate()).slice(-2);
    return `${year}-${month}-${day}`;
  }
}
