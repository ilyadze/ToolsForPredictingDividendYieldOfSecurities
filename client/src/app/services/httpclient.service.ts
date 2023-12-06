import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {UserCreateDto} from "../models/person/PersonCreateDTO";
import {SecuritiesGetDTO} from "../models/securities/SecuritiesGetDTO";
import {Observable} from "rxjs";
import {SecuritiesInfoDTO} from "../models/securities/SecuritiesInfoDTO";
import {SecuritiesSearchDTO} from "../models/securities/SecuritiesSearchDTO";
import {NewsGetDTO} from "../models/news/NewsGetDTO";
import {NewsApiResponse} from "../models/news/NewsApiResponse";
import {SecurityPriceGetDTO} from "../models/charts/SecurityPriceGetDTO";
import {SecurityDividendGetDTO} from "../models/charts/SecurityDividendGetDTO";


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
    return this.httpClient.get<SecuritiesSearchDTO[]>(`${this.base_url}/search?query=${query}`);
  }

  getSecuritiesLength(): Observable<number> {
    return this.httpClient.get<number>(this.base_url + '/length');
  }

  getSecuritiesNews(page:number): Observable<NewsApiResponse> {
    return this.httpClient.get<NewsApiResponse>(this.base_url + '/news?page=' + page);
  }
  getSecuritiesPrices(symbol:string, from: Date, to: Date): Observable<SecurityPriceGetDTO[]> {
    return this.httpClient.get<SecurityPriceGetDTO[]>(this.base_url + '/' + symbol +
        '/price?from='+ this.formatDate(from) +
        '&to='+ this.formatDate(to));
  }

  getSecuritiesDividends(symbol:string, from: Date, to: Date): Observable<SecurityDividendGetDTO[]> {
    return this.httpClient.get<SecurityDividendGetDTO[]>(this.base_url + '/' + symbol +
        '/dividends?from='+ this.formatDate(from) +
        '&to='+ this.formatDate(to));
  }

  private formatDate(date: Date): string {
    // Пример форматирования даты в строку "YYYY-MM-DD"
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const day = ('0' + date.getDate()).slice(-2);
    return `${year}-${month}-${day}`;
  }
}
