import {Component, OnInit} from '@angular/core';
import {HttpClientService} from "../../services/httpclient.service";
import {SecuritiesInfoDTO} from "../../models/securities/SecuritiesInfoDTO";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-securities-details',
  templateUrl: './securities-details.component.html',
  styleUrls: ['./securities-details.component.css']
})
export class SecuritiesDetailsComponent implements OnInit{

  securitiesInfo: SecuritiesInfoDTO;

  constructor(private httpClientService: HttpClientService,
              private route: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const symbol = params['symbol'];
      this.httpClientService.getSecuritiesInfo(symbol).subscribe(securitiesInfo => {
        this.securitiesInfo = securitiesInfo;
      });
    });
  }

  getTimeLoaded(index: number) {


    return new Date();
  }

}
