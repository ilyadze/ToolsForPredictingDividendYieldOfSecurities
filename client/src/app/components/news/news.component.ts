import {Component, OnInit} from '@angular/core';
import {NewsGetDTO} from "../../models/news/NewsGetDTO";
import {SecuritiesService} from "../../services/securities.service";
import {SecuritiesGetDTO} from "../../models/securities/SecuritiesGetDTO";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.css']
})
export class NewsComponent implements OnInit{

  length: number;
  pageSize = 10;
  pageIndex = 0;
  showFirstLastButtons = true;

  pageEvent: PageEvent;

  news: NewsGetDTO[];

  constructor(private securitiesService:SecuritiesService) {
  }

  ngOnInit() {
    this.securitiesService.getSecuritiesNews(0).subscribe(apiResponse => {
      this.news = apiResponse.content;
      this.length = apiResponse.totalPages;
    })
  }

  handlePageEvent(e: PageEvent) {
    this.pageEvent = e;
    this.pageIndex = e.pageIndex;
    this.securitiesService
        .getSecuritiesNews(this.pageIndex)
        .subscribe(newsApiResponse => {
          this.news = newsApiResponse.content;
        })
  }

}
