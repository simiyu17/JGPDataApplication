import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { ContentHeaderComponent } from '../../theme/components/content-header/content-header.component';
import { MatSort } from '@angular/material/sort';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { EditRoleComponent } from './edit-role/edit-role.component';
import { UserRoleService } from '@services/users/userroles.service';
import { UserRoleDto } from '../../dto/UserRoleDto';
import { RouterModule } from '@angular/router';
import { NoPermissionComponent } from '../errors/no-permission/no-permission.component';
import { AuthService } from '@services/users/auth.service';
import { HasPermissionDirective } from '../../directives/has-permission.directive';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-user-role',
  standalone: true,
  imports: [
    MatTableModule,
    MatPaginatorModule,
    ContentHeaderComponent,
    FlexLayoutModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    RouterModule,
    NoPermissionComponent,
    HasPermissionDirective
  ],
  templateUrl: './user-role.component.html',
  styleUrl: './user-role.component.scss'
})
export class UserRoleComponent implements OnInit, OnDestroy{
  public searchText: string;
  public page: any;
  public showSearch: boolean = false;
  public viewType: string = 'grid';
  
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  public displayedColumns = ['roleName', 'description'];
  public dataSource: any;

  userRoles: any
  private unsubscribe$ = new Subject<void>();
  constructor(public dialog: MatDialog, private userRoleServive: UserRoleService, public authService: AuthService) { }

  getAvailableUserRoles() {
    this.userRoleServive.getAvailableUserRoles()
    .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (response) => {
          this.userRoles = response;
          this.dataSource = new MatTableDataSource(this.userRoles);
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

  public changeView(viewType: string) {
    this.viewType = viewType;
    this.showSearch = false;
  }



  public openPartnerDialog(userRole: UserRoleDto | null) {
    let dialogRef = this.dialog.open(EditRoleComponent, {
      data: userRole
    });
    dialogRef.afterClosed().subscribe(() => {
      this.getAvailableUserRoles();
    });
  }

  ngOnInit(): void {
    this.getAvailableUserRoles();
  }


  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }
}
