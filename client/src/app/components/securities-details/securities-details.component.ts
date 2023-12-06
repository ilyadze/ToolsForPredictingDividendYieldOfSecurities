import {Component, OnInit} from '@angular/core';
import {SecuritiesService} from "../../services/securities.service";
import {SecuritiesInfoDTO} from "../../models/securities/SecuritiesInfoDTO";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-securities-details',
  templateUrl: './securities-details.component.html',
  styleUrls: ['./securities-details.component.css']
})
export class SecuritiesDetailsComponent implements OnInit{

  symbol: string;

  securitiesInfo: SecuritiesInfoDTO;

  constructor(private securitiesService: SecuritiesService,
              private route: ActivatedRoute) {

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

}
