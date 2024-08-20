import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { User } from '../../common/models/user.model';
import { UsersService } from '@services/users.service';
import { MatDialog } from '@angular/material/dialog';
import { UserDialogComponent } from './user-dialog/user-dialog.component'; 
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { MatButtonModule } from '@angular/material/button';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { FormsModule } from '@angular/forms';
import { NgxPaginationModule } from 'ngx-pagination';
import { MatMenuModule } from '@angular/material/menu';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { PipesModule } from '../../theme/pipes/pipes.module';
import { MatCardModule } from '@angular/material/card';
import { DatePipe, NgClass } from '@angular/common';
import { ContentHeaderComponent } from '../../theme/components/content-header/content-header.component';
import { MatDividerModule } from '@angular/material/divider';

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [
    FormsModule,
    FlexLayoutModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatMenuModule,
    MatSlideToggleModule,
    MatCardModule,
    MatDividerModule,
    NgxPaginationModule,
    PipesModule,
    DatePipe,  
    UserDialogComponent,
    ContentHeaderComponent,
    NgClass
  ],
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss',
  encapsulation: ViewEncapsulation.None,
  providers: [UsersService]
})
export class UsersComponent implements OnInit {
  public users: User[] | null;
  public searchText: string;
  public page: any;
  public showSearch: boolean = false;
  public viewType: string = 'grid';

  constructor(public dialog: MatDialog, public usersService: UsersService) { }

  ngOnInit() {
    this.getUsers();
  }

  public getUsers(): void {
    this.users = null; //for show spinner each time
    this.usersService.getUsers().subscribe(users => this.users = users);
  }
  public addUser(user: User) {
    this.usersService.addUser(user).subscribe(user => this.getUsers());
  }
  public updateUser(user: User) {
    this.usersService.updateUser(user).subscribe(user => this.getUsers());
  }
  public deleteUser(user: User) {
    this.usersService.deleteUser(user.id).subscribe(user => this.getUsers());
  }

  public changeView(viewType: string) {
    this.viewType = viewType;
    this.showSearch = false;
  }

  public onPageChanged(event: any) {
    this.page = event;
    this.getUsers();
    const mainContent = document.getElementById('main');
    if (mainContent) {
      mainContent.scrollTop = 0;
    }
  }

  public openUserDialog(user: User | null) {
    let dialogRef = this.dialog.open(UserDialogComponent, {
      data: user
    });
    dialogRef.afterClosed().subscribe(user => {
      if (user) {
        (user.id) ? this.updateUser(user) : this.addUser(user);
      }
    });
    this.showSearch = false;
  }

}