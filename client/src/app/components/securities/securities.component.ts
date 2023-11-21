import {Component, OnInit} from '@angular/core';
import {PageEvent} from "@angular/material/paginator";
import {HttpClientService} from "../../services/httpclient.service";
import {SecuritiesGetDTO} from "../../models/securities/SecuritiesGetDTO";
import {Router} from "@angular/router";


@Component({
  selector: 'app-securities',
  templateUrl: './securities.component.html',
  styleUrls: ['./securities.component.css']
})
export class SecuritiesComponent implements OnInit{

  length: number;
  securities: SecuritiesGetDTO[];
  pageSize = 20;
  pageIndex = 0;

  showFirstLastButtons = true;

  pageEvent: PageEvent;


  constructor(private httpClientService: HttpClientService,
              private router: Router) {
  }

  handlePageEvent(e: PageEvent) {
    this.pageEvent = e;
    this.length = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    console.log(this.pageIndex);
    let fromIndex = this.pageIndex * this.pageSize;
    this.httpClientService.getSecurities(fromIndex, fromIndex + this.pageSize).subscribe(securities => {
      this.securities = securities;
    })
  }

  ngOnInit(): void {
    this.httpClientService.getSecuritiesLength().subscribe(length => {
      this.length = length;
    });
    let fromIndex = this.pageIndex * this.pageSize;
    this.httpClientService.getSecurities(fromIndex, fromIndex + this.pageSize).subscribe(securities => {
      this.securities = securities;
    })
  }

  goToDetailPage(symbol: string): void {
    this.router.navigate(['/detail', symbol]); // Перейти на страницу деталей с переданным идентификатором
  }

}
