import { Component } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {HttpClientService} from "../../services/httpclient.service";
import {AuthenticationService} from "../../services/authentication.service";

@Component({
  selector: 'app-activation',
  templateUrl: './activation.component.html',
  styleUrls: ['./activation.component.css']
})
export class ActivationComponent {

  activated: string;

  constructor(
      private route: ActivatedRoute,
      private authenticationService: AuthenticationService
  ) {
      this.activateAccount();
  }

  // ngOnInit(): void {
  //   this.activateAccount();
  // }

  activateAccount(): void {
    const activationCode = this.route.snapshot.params['activationCode'];

    // Вызов сервиса для активации аккаунта
    this.authenticationService.activateAccount(activationCode)
        .subscribe(
                (result) => {
                    this.activated = result;
                },
            (error) => {
                    console.log(error);
                    if (error.status == 200) {
                        this.activated = 'Account was activate';
                    } else {
                        this.activated = 'Activation failed';

                    }

            }
        );
  }
}
