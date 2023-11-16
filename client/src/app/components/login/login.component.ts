import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../../services/authentication.service";
import {Router} from "@angular/router";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  loginForm: FormGroup;

  @Input() error: string | null;

  constructor(private formBuilder:FormBuilder,
              private authService: AuthenticationService,
              private router: Router) {
  }
  ngOnInit(): void {
    this.loginForm = this.createLoginForm();
  }

  createLoginForm() {
    return this.formBuilder.group({
      email: ['', Validators.compose([Validators.required,Validators.email])],
      password: ['', Validators.compose([Validators.required])]
    })
  }

  login() {
    this.authService.authenticate(
      this.loginForm.value.email,
      this.loginForm.value.password
    ).subscribe(data => {
        this.router.navigate(['']);
      },
      error => {
        this.error = 'Incorrect data';
      }
    );
  }
}
