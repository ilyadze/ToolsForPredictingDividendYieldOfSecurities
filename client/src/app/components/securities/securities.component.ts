import {Component, OnInit} from '@angular/core';
import {PageEvent} from "@angular/material/paginator";
import {SecuritiesService} from "../../services/securities.service";
import {SecuritiesGetDTO} from "../../models/securities/SecuritiesGetDTO";
import {Router} from "@angular/router";
import {Sort} from "@angular/material/sort";
import {FormControl} from "@angular/forms";
import {Filters} from "../../models/securities/Filters";


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

  filters: Filters;



  constructor(private securitiesService: SecuritiesService,
              private router: Router) {
  }

  handlePageEvent(e: PageEvent) {
    this.pageEvent = e;
    this.length = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    let fromIndex = this.pageIndex * this.pageSize;
    this.securitiesService
      .getSecurities(fromIndex, (fromIndex + this.pageSize)<this.length?fromIndex + this.pageSize:this.length)
      .subscribe(securities => {
      this.securities = securities;
    })
  }

  ngOnInit(): void {
    this.securitiesService.getSecuritiesLength().subscribe(length => {
      this.length = length;
    });
    let fromIndex = this.pageIndex * this.pageSize;
    this.securitiesService.getSecurities(fromIndex, fromIndex + this.pageSize).subscribe(securities => {
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
        case 'price':
          return compare(a.price, b.price, isAsc);
        case 'symbol':
          return compare(a.symbol, b.symbol, isAsc);
        case 'exchange':
          return compare(a.exchange, b.exchange, isAsc);
        case 'type':
          return compare(a.type, b.type, isAsc);
        default:
          return 0;
      }
    });
  }
  handleFormData(formData: any) {
    this.filters=formData;
    let fromIndex = this.pageIndex * this.pageSize;
    this.securitiesService.getFiltersData(fromIndex, fromIndex + this.pageSize, this.filters).subscribe(securities => {
      this.securities = securities;
    })
    // Ваши действия с данными формы, например, отправка данных на сервер
  }
}
function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}


