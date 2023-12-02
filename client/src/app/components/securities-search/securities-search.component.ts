import {Component, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";
import {debounceTime, distinctUntilChanged, switchMap} from "rxjs/operators";
import {HttpClientService} from "../../services/httpclient.service";
import {SecuritiesSearchDTO} from "../../models/securities/SecuritiesSearchDTO";
import {Observable} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'app-securities-search',
  templateUrl: './securities-search.component.html',
  styleUrls: ['./securities-search.component.css']
})
export class SecuritiesSearchComponent {
  searchControl = new FormControl();
  searchResults$: Observable<SecuritiesSearchDTO[]>;

  constructor(private searchService: HttpClientService, private router: Router) {
    this.searchResults$ = this.searchControl.valueChanges.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(value => this.searchService.search(value))
    );
  }

  goToDetailPage(symbol: string): void {
    this.router.navigate(['/securities', symbol]); // Перейти на страницу деталей с переданным идентификатором
  }
}
// search.component.ts
