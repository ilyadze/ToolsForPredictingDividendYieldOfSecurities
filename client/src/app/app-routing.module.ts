import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import {SecuritiesComponent} from "./components/securities/securities.component";
import {ProfileComponent} from "./components/profile/profile.component";
import {SecuritiesDetailsComponent} from "./components/securities-details/securities-details.component";
import {NewsComponent} from "./components/news/news.component";
import {WalletComponent} from "./components/wallet/wallet.component";

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'sign-up', component: RegistrationComponent },
  { path: 'securities', component: SecuritiesComponent},
  { path: 'profile', component: ProfileComponent},
  {path: 'securities/:symbol', component: SecuritiesDetailsComponent},
  {path: 'news', component: NewsComponent},
  {path: 'wallet', component: WalletComponent},
  { path: '**',redirectTo: 'securities', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
