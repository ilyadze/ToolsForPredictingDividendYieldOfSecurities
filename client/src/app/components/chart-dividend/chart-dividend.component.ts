import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {Chart} from "chart.js";
import {HttpClientService} from "../../services/httpclient.service";
import {SecurityPriceGetDTO} from "../../models/charts/SecurityPriceGetDTO";
import {SecurityDividendGetDTO} from "../../models/charts/SecurityDividendGetDTO";

@Component({
  selector: 'app-chart-dividend',
  templateUrl: './chart-dividend.component.html',
  styleUrls: ['./chart-dividend.component.css']
})
export class ChartDividendComponent implements OnInit{

  @Input() someData: any;
  @ViewChild('myChart') myChart!: ElementRef;
  prices: SecurityDividendGetDTO[];

  constructor(private httpClientService: HttpClientService) {
  }


  renderChart() {
    const ctx = this.myChart.nativeElement.getContext('2d');
    new Chart(ctx, {
      type: 'line',
      data: {
        labels: this.prices.map(item => item.label),
        datasets: [{
          data: this.prices.map(item => item.dividend),
          // backgroundColor: ['red', 'green', 'blue'],
        }],
      },
    });
  }

  ngOnInit(): void {
    this.httpClientService.getSecuritiesDividends(this.someData).subscribe(result => {
      this.prices = result;
      this.renderChart();
    });
  }
}
