import { Component, ViewChild } from '@angular/core';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { MatCardModule } from '@angular/material/card';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { ContentHeaderComponent } from '../../theme/components/content-header/content-header.component';
import { DataUploadComponent } from './data-upload/data-upload.component';
import { LoanService } from '@services/data-management/loan.service';
import { NoPermissionComponent } from '../errors/no-permission/no-permission.component';
import { AuthService } from '@services/users/auth.service';

@Component({
  selector: 'app-lending-data',
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
    MatPaginatorModule,
    NoPermissionComponent
  ],
  templateUrl: './lending-data.component.html',
  styleUrl: './lending-data.component.scss'
})
export class LendingDataComponent {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  public displayedColumns = [
    'participantName', 'loanNumber', 'pipeLineSource', 
    'loanAmountAccessed', 'loanOutStandingAmount', 
    'loanDuration', 'dateApplied', 'loanQuality'
  ];
  public dataSource: any;

  newLoansData: any

  constructor(private loanService: LoanService, public authService: AuthService) { }

  getAvailableNewLendingData() {
    this.loanService.getAvailableNewLendingData()
      .subscribe({
        next: (response) => {
          this.newLoansData = response;
          this.dataSource = new MatTableDataSource(this.newLoansData);
        },
        error: (error) => { }
      });
  }

  
  ngAfterViewInit() {
    if(this.dataSource){
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    }
  }

  ngOnInit(): void {
    this.getAvailableNewLendingData();
  }
}
