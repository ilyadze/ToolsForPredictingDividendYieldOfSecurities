import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {SecuritiesService} from "../../services/securities.service";
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
  chart: Chart;
  prices: SecurityPriceGetDTO[];
  startDate: Date = new Date('2023-11-05');
  endDate: Date = new Date();

  constructor(private httpClientService: SecuritiesService) {
  }


  renderChart() {
    const ctx = this.myChart.nativeElement.getContext('2d');
    this.chart = new Chart(ctx, {
      type: 'line',
      data: {
        labels: this.prices.map(item => item.date).reverse(),
        datasets: [{
          data: this.prices.map(item => item.high).reverse(),
          // backgroundColor: ['red', 'green', 'blue'],
        }],
      },
    });
  }

  ngOnInit(): void {
    this.httpClientService.getSecuritiesPrices(this.someData, this.startDate, this.endDate).subscribe(result => {
      this.prices = result;
      this.renderChart();
    });
  }

  removeData() {
    // this.chart.data.labels.pop();
    this.chart.data.datasets.forEach((dataset) => {
      dataset.data.pop();
    });
    this.chart.update();
  }


  addEvent() {
    this.httpClientService.getSecuritiesPrices(this.someData, this.startDate, this.endDate).subscribe(result => {
      this.prices = result;
      this.chart.data.labels =  result.map(item => item.date).reverse();
      this.chart.data.datasets[0].data = result.map(item => item.high).reverse();
      this.chart.update();
    });
  }
}
