import {Component, OnInit} from '@angular/core';
import {WalletSecuritiesAddDTO} from "../../models/wallet/WalletSecuritiesAddDTO";
import {WalletService} from "../../services/wallet.service";
import {Router} from "@angular/router";
import {WalletSecurityGetDTO} from "../../models/wallet/WalletSecurityGetDTO";
import {Sort} from "@angular/material/sort";

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.css']
})
export class WalletComponent implements OnInit{
  length: number;
  securities: WalletSecurityGetDTO[];

  constructor(private walletService: WalletService,
               private router: Router) {
  }

  ngOnInit(): void {
    this.walletService.getSecurities().subscribe(result => {
      this.securities = result;
    });
  }

  goToDetailPage(symbol: string): void {
    this.router.navigate(['/securities', symbol]); // Перейти на страницу деталей с переданным идентификатором
  }

  deleteSecurity(symbol: string) {
    this.walletService.deleteSecurity(symbol).subscribe(() => {
      this.walletService.getSecurities().subscribe(result => {
        this.securities = result;
      });
    });
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
        case 'actualPrice':
          return compare(a.actualPrice, b.actualPrice, isAsc);
        case 'totalPrice':
          return compare(a.totalPrice, b.totalPrice, isAsc);
        case 'priceChange':
          return compare(a.priceChange, b.priceChange, isAsc);
        case 'quantity':
          return compare(a.quantity, b.quantity, isAsc);
        default:
          return 0;
      }
    });
  }
}
function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}

