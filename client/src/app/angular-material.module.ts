import { NgModule } from '@angular/core';

import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import {MatListModule} from '@angular/material/list';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSortModule} from '@angular/material/sort';
import {MatMenuModule} from '@angular/material/menu';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatTabsModule} from '@angular/material/tabs';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatSelectModule} from '@angular/material/select';
import {MatExpansionModule} from '@angular/material/expansion';

@NgModule({
  imports: [
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    MatPaginatorModule,
    MatButtonToggleModule,
    MatListModule,
    MatToolbarModule,
    MatSortModule,
    MatMenuModule,
    MatAutocompleteModule,
    MatGridListModule,
    MatTabsModule,
    MatDatepickerModule,
    MatSelectModule,
    MatExpansionModule,

  ],
  exports: [
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    MatPaginatorModule,
    MatButtonToggleModule,
    MatListModule,
    MatToolbarModule,
    MatSortModule,
    MatMenuModule,
    MatAutocompleteModule,
    MatGridListModule,
    MatTabsModule,
    MatDatepickerModule,
    MatSelectModule,
    MatExpansionModule
  ]
})

export class AngularMaterialModule { }
