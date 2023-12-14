import {Component, ElementRef, Input, ViewChild} from '@angular/core';
import {SecurityDividendGetDTO} from "../../models/charts/SecurityDividendGetDTO";
import {Chart} from "chart.js";
import {SecuritiesService} from "../../services/securities.service";
import {WalletService} from "../../services/wallet.service";

@Component({
  selector: 'app-dividends',
  templateUrl: './dividends.component.html',
  styleUrls: ['./dividends.component.css']
})
export class DividendsComponent {
  @Input() someData: any;
  @ViewChild('myChart') myChart!: ElementRef;
  dividends: SecurityDividendGetDTO[];
  chart: Chart;


  constructor(private walletService: WalletService) {
  }


  renderChart() {
    const ctx = this.myChart.nativeElement.getContext('2d');
    this.chart = new Chart(ctx, {
      type: 'line',
      data: {
        labels: this.dividends.map(item => item.label).reverse(),
        datasets: [{
          label: this.someData,
          data: this.dividends.map(item => item.dividend).reverse(),
          // backgroundColor: ['red', 'green', 'blue'],
        }],
      },
    });
  }

  ngOnInit(): void {
    this.walletService.getSecuritiesDividends(this.someData).subscribe(result => {
      this.dividends = result;
      this.renderChart();

    });
  }
}
