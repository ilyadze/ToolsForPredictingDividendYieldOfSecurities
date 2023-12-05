import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {SecurityDividendGetDTO} from "../../models/charts/SecurityDividendGetDTO";
import {HttpClientService} from "../../services/httpclient.service";
import {Chart} from "chart.js";
import {SecurityPriceGetDTO} from "../../models/charts/SecurityPriceGetDTO";

@Component({
  selector: 'app-chart-prices',
  templateUrl: './chart-prices.component.html',
  styleUrls: ['./chart-prices.component.css']
})
export class ChartPricesComponent implements OnInit{
  @Input() someData: any;
  @ViewChild('myChart') myChart!: ElementRef;
  prices: SecurityPriceGetDTO[];

  constructor(private httpClientService: HttpClientService) {
  }


  renderChart() {
    const ctx = this.myChart.nativeElement.getContext('2d');
    new Chart(ctx, {
      type: 'line',
      data: {
        labels: this.prices.map(item => item.date),
        datasets: [{
          data: this.prices.map(item => item.high),
          // backgroundColor: ['red', 'green', 'blue'],
        }],
      },
    });
  }

  ngOnInit(): void {
    this.httpClientService.getSecuritiesPrices(this.someData).subscribe(result => {
      this.prices = result;
      this.renderChart();
    });
  }
}
