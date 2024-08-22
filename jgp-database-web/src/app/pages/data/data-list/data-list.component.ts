import { Component, ViewChild } from '@angular/core';
import { ContentHeaderComponent } from '../../../theme/components/content-header/content-header.component';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { MatCardModule } from '@angular/material/card';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { DataUploadComponent } from "../data-upload/data-upload.component";
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { ClientService } from '@services/clients.service';
import { BMOClientDataService } from '@services/bmo-client-data.service';

@Component({
  selector: 'app-data-list',
  standalone: true,
  imports: [
    ContentHeaderComponent,
    FlexLayoutModule,
    MatCardModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatIconModule,
    DataUploadComponent,
    MatTableModule,
    MatPaginatorModule
],
  templateUrl: './data-list.component.html'
})
export class DataListComponent {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  public displayedColumns = ['clientName', 'tasAttended', 'taSessionsAttended', 'isRecommendedForFinance', 'dateFormSubmitted', 'decisionDate'];
  public dataSource: any;

  bmoClientsData: any

  constructor(private bmoClientDataService: BMOClientDataService) { }

  getAvailableBMOClientData() {
    this.bmoClientDataService.getAvailableBMOClientData()
      .subscribe({
        next: (response) => {
          this.bmoClientsData = response;
          this.dataSource = new MatTableDataSource(this.bmoClientsData);
        },
        error: (error) => { }
      });
  }

  
  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  ngOnInit(): void {
    this.getAvailableBMOClientData();
  }
}
