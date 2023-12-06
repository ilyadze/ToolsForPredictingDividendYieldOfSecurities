import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {UserCreateDto} from "../../models/person/PersonCreateDTO";
import {SecuritiesService} from "../../services/securities.service";
import {AuthenticationService} from "../../services/authentication.service";


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit{

  registerForm: FormGroup;

  // user: UserCreateDto;

  constructor(private formBuilder: FormBuilder,
              private authenticationService: AuthenticationService,
              private router: Router) {

  }


  ngOnInit(): void {
    this.registerForm = this.createFormRegistration();
  }

  createFormRegistration() {
    return this.formBuilder.group({
        email: ['', Validators.compose([Validators.required, Validators.email])],
        username: ['', Validators.compose([Validators.required])],
        firstname: ['', Validators.compose([Validators.required])],
        lastname: ['', Validators.compose([Validators.required])],
        password: ['', Validators.compose([Validators.required])],
        confirmPassword: ['', Validators.compose([Validators.required])],
      }
    )
  }

  register() {
    // this.user = this.registerForm.getRawValue();
    this.authenticationService.registerUser({
      email: this.registerForm.value.email,
      username: this.registerForm.value.username,
      firstname: this.registerForm.value.firstname,
      lastname: this.registerForm.value.lastname,
      password: this.registerForm.value.password,
      // confirmPassword: this.registerForm.value.confirmPassword,
    }).subscribe(
      () => {
        this.router.navigate(['/login']);
      }
    );
  }

}
