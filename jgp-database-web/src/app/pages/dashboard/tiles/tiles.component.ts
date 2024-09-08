import { Component, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { DashboardService } from '@services/dashboard.service';
import { HighLevelSummaryDto } from '../dto/highLevelSummaryDto';

@Component({
  selector: 'app-tiles',
  standalone: true,
  imports: [
    FlexLayoutModule,
    MatCardModule,
    MatIconModule
  ],
  templateUrl: './tiles.component.html',
  styleUrl: './tiles.component.scss'
})
export class TilesComponent implements OnInit{

  highLevelSummary: HighLevelSummaryDto = {businessesTrained: 0, businessesLoaned: 0, amountDisbursed: 0, outStandingAmount: 0}
  constructor(private dashBoardService: DashboardService){

  }

  getHighLevelSummary() {
    this.dashBoardService.getHighLevelSummary()
      .subscribe({
        next: (response) => {
          this.highLevelSummary = response;
        },
        error: (error) => { }
      });
  }

  ngOnInit(): void {
    this.getHighLevelSummary();
  }

}
