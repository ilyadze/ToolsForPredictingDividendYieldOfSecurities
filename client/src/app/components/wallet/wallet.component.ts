import {Component, OnInit} from '@angular/core';
import {WalletSecuritiesAddDTO} from "../../models/wallet/WalletSecuritiesAddDTO";
import {WalletService} from "../../services/wallet.service";
import {Router} from "@angular/router";
import {WalletSecurityGetDTO} from "../../models/wallet/WalletSecurityGetDTO";

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
}
