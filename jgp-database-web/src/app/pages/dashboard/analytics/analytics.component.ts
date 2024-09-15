import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { analytics } from '@data/dashboard-data';
import { DashboardService } from '@services/dashboard.service';
import { NgxChartsModule } from '@swimlane/ngx-charts';

@Component({
  selector: 'app-analytics',
  standalone: true,
  imports: [
    MatCardModule,
    NgxChartsModule
  ],
  templateUrl: './analytics.component.html',
  styleUrl: './analytics.component.scss'
})
export class AnalyticsComponent implements OnInit {
  public chartSColorScheme: any = {
    domain: ['#2F3E9E', '#D22E2E', '#378D3B', '#7f7f7f', '#c4a678', '#6a7b6a', '#191919', '#3d144c', '#f0e1dc', '#a04324', '#00ffff', '#0e5600', '#0e9697']
  };
  public autoScale = true;
  public roundDomains = true;
  public gradient = false;

  public lastThreeYearLoansAccessedPerPartner: any[];
  public lastThreeYearLoansAccessedPerPartnerShowXAxis = true;
  public lastThreeYearLoansAccessedPerPartnerShowYAxis = true;
  public lastThreeYearLoansAccessedPerPartnerShowLegend = false;
  public lastThreeYearLoansAccessedPerPartnerShowXAxisLabel = true;
  public lastThreeYearLoansAccessedPerPartnerXAxisLabel = 'Year';
  public lastThreeYearLoansAccessedPerPartnerShowYAxisLabel = true;
  public lastThreeYearLoansAccessedPerPartnerYAxisLabel = 'Accessed Loans';
  public lastThreeYearLoansAccessedPerPartnerTitle = 'Loans Accessed Per Partner Vs Last 2 Years';
  

  public analytics: any[];
  public showXAxis = true;
  public showYAxis = true;
  public showLegend = false;
  public showXAxisLabel = true;
  public xAxisLabel = 'Year';
  public showYAxisLabel = true;
  public yAxisLabel = 'Placeholder';

  
  @ViewChild('resizedDiv') resizedDiv: ElementRef;
  public previousWidthOfResizedDiv: number = 0;

  constructor(private dashBoardService: DashboardService){
    
  }

  ngOnInit() {
    this.analytics = analytics;
    this.getLastThreeYearsAccessedLoanPerPartnerSummary();
  }

  getLastThreeYearsAccessedLoanPerPartnerSummary() {
    this.dashBoardService.getLastThreeYearsAccessedLoanPerPartnerSummary()
      .subscribe({
        next: (response) => {
          this.lastThreeYearLoansAccessedPerPartner = response;
        },
        error: (error) => { }
      });
  }

  onSelect(event: any) {
    console.log(event);
  }

  ngAfterViewChecked() {
    if (this.previousWidthOfResizedDiv != this.resizedDiv.nativeElement.clientWidth) {
      this.analytics = [...analytics];
    }
    this.previousWidthOfResizedDiv = this.resizedDiv.nativeElement.clientWidth;
  }

}
