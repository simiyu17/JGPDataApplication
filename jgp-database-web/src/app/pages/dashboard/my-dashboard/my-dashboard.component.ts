import { Component } from '@angular/core';
import { FiDashboardComponent } from '../fi-dashboard/fi-dashboard.component';
import { BmoDashboardComponent } from '../bmo-dashboard/bmo-dashboard.component';
import { AuthService } from '@services/users/auth.service';
import { DashboardComponent } from "../dashboard.component";

@Component({
  selector: 'app-my-dashboard',
  standalone: true,
  imports: [
    FiDashboardComponent,
    BmoDashboardComponent,
    DashboardComponent
],
  templateUrl: './my-dashboard.component.html',
  styleUrl: './my-dashboard.component.scss'
})
export class MyDashboardComponent {

  partnerType: string | undefined = 'NONE';
  constructor(private authService: AuthService){

  }
  ngOnInit(): void {
    this.partnerType = this.authService.currentUser()?.partnerType === '-' ? 'NONE' : this.authService.currentUser()?.partnerType;
  }
}
