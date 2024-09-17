import { Component, OnInit } from '@angular/core';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { ContentHeaderComponent } from '../../../theme/components/content-header/content-header.component';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { ActivatedRoute } from '@angular/router';
import { map, Observable } from 'rxjs';
import { AsyncPipe } from '@angular/common';
import { UserRoleDto } from '../../../dto/UserRoleDto';

@Component({
  selector: 'app-user-role-details',
  standalone: true,
  imports: [
    MatTableModule,
    MatPaginatorModule,
    ContentHeaderComponent,
    FlexLayoutModule,
    MatButtonModule,
    MatIconModule,
    AsyncPipe
  ],
  templateUrl: './user-role-details.component.html',
  styleUrl: './user-role-details.component.scss'
})
export class UserRoleDetailsComponent implements OnInit {

  userRole: Observable<UserRoleDto>;
  constructor(private activatedRoute: ActivatedRoute){}

  ngOnInit(): void {
    this.userRole = this.activatedRoute.data.pipe(map(data => data['userRole']));
  }

}
