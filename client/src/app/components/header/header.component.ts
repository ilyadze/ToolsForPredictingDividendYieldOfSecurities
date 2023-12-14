import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/authentication.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{
  constructor(private router: Router,
              public authenticationService:AuthenticationService) {
  }
  goToMainPage() {
    this.router.navigate(['securities']);
  }
  goToNews() {
    this.router.navigate(['news']);
  }

  goToWallet() {
    this.router.navigate(['wallet']);
  }

  goToLoginPage() {
    this.router.navigate(['login']);
  }

  goToProfilePage() {
    this.router.navigate(['profile']);
  }

  goToDividends() {
    this.router.navigate(['dividends']);
  }

  logout(): void {
    this.authenticationService.logOut();
    this.router.navigate(['/login']);
  }

  ngOnInit(): void {
  }

  protected readonly ongotpointercapture = ongotpointercapture;
}

