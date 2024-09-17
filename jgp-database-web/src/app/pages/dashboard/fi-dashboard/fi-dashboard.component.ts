import { Component, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { ContentHeaderComponent } from '../../../theme/components/content-header/content-header.component';
import { TilesComponent } from '../tiles/tiles.component';
import { InfoCardsComponent } from '../info-cards/info-cards.component';
import { AnalyticsComponent } from '../analytics/analytics.component';
import { AuthService } from '@services/users/auth.service';
@Component({
  selector: 'app-fi-dashboard',
  standalone: true,
  imports: [
    ContentHeaderComponent,
    FlexLayoutModule,
    MatCardModule,
    MatProgressBarModule,
    TilesComponent,
    InfoCardsComponent,
    AnalyticsComponent
  ],
  templateUrl: './fi-dashboard.component.html',
  styleUrl: './fi-dashboard.component.scss'
})
export class FiDashboardComponent implements OnInit {

  partnerName: string = '';
  partnerId: number;
  constructor(private authService: AuthService){

  }
  ngOnInit(): void {
    this.partnerName = `${this.authService.currentUser().partner} Dashboard !`;
    this.partnerId = this.authService.currentUser().partnerId;
  }

}
