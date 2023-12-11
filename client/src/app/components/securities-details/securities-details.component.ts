import {Component, OnInit} from '@angular/core';
import {SecuritiesService} from "../../services/securities.service";
import {SecuritiesInfoDTO} from "../../models/securities/SecuritiesInfoDTO";
import {ActivatedRoute} from "@angular/router";
import {WalletService} from "../../services/wallet.service";
import {WalletSecuritiesAddDTO} from "../../models/wallet/WalletSecuritiesAddDTO";

@Component({
  selector: 'app-securities-details',
  templateUrl: './securities-details.component.html',
  styleUrls: ['./securities-details.component.css']
})
export class SecuritiesDetailsComponent implements OnInit{

  symbol: string;

  securitiesInfo: SecuritiesInfoDTO;

  constructor(private securitiesService: SecuritiesService,
              private route: ActivatedRoute,
              private walletService: WalletService) {

  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.symbol = params['symbol'];
      this.securitiesService.getSecuritiesInfo(this.symbol).subscribe(securitiesInfo => {
        this.securitiesInfo = securitiesInfo;
      });
    });
  }

  getTimeLoaded(index: number) {
    return new Date();
  }

  addToWallet() {
    let security = {
      name: this.securitiesInfo.companyName, // Используем поле companyName из SecuritiesInfoDTO
      symbol: this.securitiesInfo.symbol,
      price: parseFloat(this.securitiesInfo.price), // Преобразуем строку в число
      currency: this.securitiesInfo.currency,
      quantity: 1,
      dateOfPurchase: '',
    }
    this.walletService.addSecurities(security);
  }

}
