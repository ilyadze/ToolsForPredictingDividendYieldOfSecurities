import {Component, OnInit} from '@angular/core';
import {PageEvent} from "@angular/material/paginator";
import {HttpClientService} from "../../services/httpclient.service";
import {SecuritiesGetDTO} from "../../models/securities/SecuritiesGetDTO";
import {Router} from "@angular/router";
import {Sort} from "@angular/material/sort";


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
    this.router.navigate(['/securities', symbol]); // Перейти на страницу деталей с переданным идентификатором
  }


  sortData(sort: Sort) {
    const data = this.securities.slice();
    if (!sort.active || sort.direction === '') {
      return;
    }

    this.securities = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'name':
          return compare(a.name, b.name, isAsc);
        case 'region':
          return compare(a.region, b.region, isAsc);
        case 'symbol':
          return compare(a.symbol, b.symbol, isAsc);
        case 'exchange':
          return compare(a.exchange, b.exchange, isAsc);
        case 'currency':
          return compare(a.currency, b.currency, isAsc);
        default:
          return 0;
      }
    });
  }
}
function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}


