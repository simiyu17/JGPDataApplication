import { Component, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { ContentHeaderComponent } from '../../theme/components/content-header/content-header.component';
import { TablesService, Element } from '@services/tables.service';
import { ClientService } from '@services/clients.service';
import { MatSort } from '@angular/material/sort';

@Component({
  selector: 'app-clients',
  standalone: true,
  imports: [
    MatTableModule,
    MatPaginatorModule,
    ContentHeaderComponent
  ],
  templateUrl: './clients.component.html',
  styleUrl: './clients.component.scss',
  providers: [
    TablesService
  ]
})
export class ClientsComponent {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  public displayedColumns = ['businessName', 'jgpId', 'phoneNumber', 'ownerGender', 'businessLocation'];
  public dataSource: any;

  clients: any
  constructor(private tablesService: TablesService, private clientService: ClientService) { }

  getAvailableClients() {
    this.clientService.getAvailableClients()
      .subscribe({
        next: (response) => {
          this.clients = response;
          this.dataSource = new MatTableDataSource(this.clients);
        },
        error: (error) => { }
      });
  }

  
  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  ngOnInit(): void {
    this.getAvailableClients();
  }
}
