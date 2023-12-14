import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import {SecuritiesComponent} from "./components/securities/securities.component";
import {ProfileComponent} from "./components/profile/profile.component";
import {SecuritiesDetailsComponent} from "./components/securities-details/securities-details.component";
import {NewsComponent} from "./components/news/news.component";
import {WalletComponent} from "./components/wallet/wallet.component";
import {ActivationComponent} from "./components/activation/activation.component";
import {AuthGaurdService} from "./services/auth-gaurd.service";
import {DividendsComponent} from "./components/dividends/dividends.component";

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'sign-up', component: RegistrationComponent },
  { path: 'securities', component: SecuritiesComponent},
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGaurdService] },
  {path: 'securities/:symbol', component: SecuritiesDetailsComponent},
  {path: 'news', component: NewsComponent},
  {path: 'wallet', component: WalletComponent, canActivate: [AuthGaurdService] },
  {path: 'dividends', component: DividendsComponent, canActivate: [AuthGaurdService] },
  { path: 'activate/:activationCode', component: ActivationComponent },
  { path: '**',redirectTo: 'securities', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
