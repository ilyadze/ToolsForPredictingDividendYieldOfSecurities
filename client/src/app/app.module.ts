import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { HeaderComponent } from './components/header/header.component';
import {AngularMaterialModule} from "./angular-material.module";
import {SecuritiesComponent} from "./components/securities/securities.component";
import {ProfileComponent} from "./components/profile/profile.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import {SecuritiesDetailsComponent} from "./components/securities-details/securities-details.component";
import {SecuritiesSearchComponent} from "./components/securities-search/securities-search.component";
import {ChartDividendComponent} from "./components/chart-dividend/chart-dividend.component";
import { NgChartsModule } from 'ng2-charts';
import {NewsComponent} from "./components/news/news.component";
import {WalletComponent} from "./components/wallet/wallet.component";
import {ChartPricesComponent} from "./components/chart-prices/chart-prices.component";
import {MatNativeDateModule} from "@angular/material/core";
import {ActivationComponent} from "./components/activation/activation.component";
import {FilterComponent} from "./components/filter/filter.component";
import {DividendsComponent} from "./components/dividends/dividends.component";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    HeaderComponent,
    SecuritiesComponent,
    ProfileComponent,
    SecuritiesDetailsComponent,
    SecuritiesSearchComponent,
    ChartDividendComponent,
    NewsComponent,
    WalletComponent,
    ChartPricesComponent,
    ActivationComponent,
    FilterComponent,
    DividendsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AngularMaterialModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    NgChartsModule,
    BrowserModule,
    MatNativeDateModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
