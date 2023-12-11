import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {PersonService} from "../../services/person.service";
import {PersonGetDTO} from "../../models/person/PersonGetDTO";
import {ActivatedRoute, Router} from "@angular/router";
import {WalletSecurityGetDTO} from "../../models/wallet/WalletSecurityGetDTO";
import {WalletService} from "../../services/wallet.service";
import {Chart} from "chart.js";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit{
  person: PersonGetDTO;
  @ViewChild('myChart') myChart!: ElementRef;
  securities: WalletSecurityGetDTO[];
  chart: Chart;


  constructor(private personService:PersonService,
              private walletService: WalletService,
              private router: Router) {
  }

  ngOnInit(): void {
      this.personService.getPerson().subscribe(personInfo => {
        this.person = personInfo;
      });
    this.walletService.getSecurities().subscribe(result => {
      this.securities = result;
      this.renderChart();
    });
  }

  renderChart() {
    const ctx = this.myChart.nativeElement.getContext('2d');
    new Chart(ctx, {
      type: 'doughnut',
      data: {
        labels: this.securities.map(item => item.name).reverse(),
        datasets: [{
          data: this.securities.map(item => item.totalPrice).reverse(),
          // backgroundColor: ['red', 'green', 'blue'],
        }],
      },
    });
  }

  goToWalletPage() {
    this.router.navigate(['wallet']);
  }

}
