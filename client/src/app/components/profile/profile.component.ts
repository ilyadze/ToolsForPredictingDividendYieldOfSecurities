import {Component, OnInit} from '@angular/core';
import {PersonService} from "../../services/person.service";
import {PersonGetDTO} from "../../models/person/PersonGetDTO";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit{
  person: PersonGetDTO;

  constructor(private personService:PersonService) {
  }

  ngOnInit(): void {
      this.personService.getPerson().subscribe(personInfo => {
        this.person = personInfo;
      });

  }

}
