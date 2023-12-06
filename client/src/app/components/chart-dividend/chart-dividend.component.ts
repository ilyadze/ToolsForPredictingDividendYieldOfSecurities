import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {Chart} from "chart.js";
import {HttpClientService} from "../../services/httpclient.service";
import {SecurityPriceGetDTO} from "../../models/charts/SecurityPriceGetDTO";
import {SecurityDividendGetDTO} from "../../models/charts/SecurityDividendGetDTO";
import {MatDatepickerInputEvent} from "@angular/material/datepicker";

@Component({
  selector: 'app-chart-dividend',
  templateUrl: './chart-dividend.component.html',
  styleUrls: ['./chart-dividend.component.css']
})
export class ChartDividendComponent implements OnInit{

  @Input() someData: any;
  @ViewChild('myChart') myChart!: ElementRef;
  prices: SecurityDividendGetDTO[];
  startDate: Date = new Date('2020-11-05');
  endDate: Date = new Date();
  chart: Chart;

  constructor(private httpClientService: HttpClientService) {
  }


  renderChart() {
    const ctx = this.myChart.nativeElement.getContext('2d');
    this.chart = new Chart(ctx, {
      type: 'line',
      data: {
        labels: this.prices.map(item => item.label).reverse(),
        datasets: [{
          data: this.prices.map(item => item.dividend).reverse(),
          // backgroundColor: ['red', 'green', 'blue'],
        }],
      },
    });
  }

  ngOnInit(): void {
    this.httpClientService.getSecuritiesDividends(this.someData, this.startDate, this.endDate).subscribe(result => {
      this.prices = result;
      this.renderChart();
    });
  }

  addEvent() {
    this.httpClientService.getSecuritiesDividends(this.someData, this.startDate, this.endDate).subscribe(result => {
      this.chart.data.labels =  result.map(item => item.date).reverse();
      this.chart.data.datasets[0].data = result.map(item => item.dividend).reverse();
      this.chart.update();
    });
  }
}
