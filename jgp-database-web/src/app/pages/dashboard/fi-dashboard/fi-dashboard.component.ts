import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { ContentHeaderComponent } from '../../../theme/components/content-header/content-header.component';
import { TilesComponent } from '../tiles/tiles.component';
import { InfoCardsComponent } from '../info-cards/info-cards.component';
import { AnalyticsComponent } from '../analytics/analytics.component';
import { AuthService } from '@services/users/auth.service';
import { DashboardService } from '@services/dashboard/dashboard.service';
import { MatIconModule } from '@angular/material/icon';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { PieChartComponent } from '../pie-chart/pie-chart.component';
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
    AnalyticsComponent,
    FlexLayoutModule,
    MatCardModule,
    MatIconModule,
    NgxChartsModule,
    PieChartComponent
  ],
  templateUrl: './fi-dashboard.component.html',
  styleUrl: './fi-dashboard.component.scss'
})
export class FiDashboardComponent implements OnInit {

  partnerName: string = '';
  partnerId: number;
  public gradient = false;
  public autoScale = true;
  @ViewChild('resizedDiv') resizedDiv: ElementRef;
  public previousWidthOfResizedDiv: number = 0;

  public loansDisbursedByGender: any[];
  public loansDisbursedByGenderShowLegend: boolean = false;
  public chartSColorScheme: any = {
    domain: ['#2F3E9E', '#D22E2E', '#378D3B', '#7f7f7f', '#c4a678', '#6a7b6a', '#191919', '#3d144c', '#f0e1dc', '#a04324', '#00ffff', '#0e5600', '#0e9697']
  };
  public loansDisbursedByGenderShowLabels: boolean = true;
  public loansDisbursedByGenderExplodeSlices: boolean = false;
  public loansDisbursedByGenderDoughnut: boolean = true;
  public loansDisbursedByGenderChartTitle: string = 'Loan Disbursed by Gender';


  public loansDisbursedByPipeline: any[];
  public loansDisbursedByPipelineShowLegend: boolean = false;
  public loansDisbursedByPipelineShowLabels: boolean = true;
  public loansDisbursedByPipelineExplodeSlices: boolean = false;
  public loansDisbursedByPipelineDoughnut: boolean = false;
  public loansDisbursedByPipelineChartTitle: string = 'Loan Disbursed by Pipeline Source';

  public loansDisbursedByQuality: any[];
  public loansDisbursedByQualityShowXAxis: boolean = true;
  public loansDisbursedByQualityShowYAxis: boolean = true;
  public loansDisbursedByQualityShowLegend: boolean = false;
  public loansDisbursedByQualityShowXAxisLabel: boolean = true;
  public loansDisbursedByQualityShowYAxisLabel: boolean = true;
  public loansDisbursedByQualityXAxisLabel: string = 'Quality';
  public loansDisbursedByQualityYAxisLabel: string = 'Amount Disbursed';
  public loansDisbursedByQualityChartTitle: string = 'Loan Disbursed by Pipeline Source';
  constructor(private authService: AuthService, private dashBoardService: DashboardService){

  }

  ngOnInit() {
    this.partnerName = `${this.authService.currentUser().partner} Dashboard !`;
    this.partnerId = this.authService.currentUser().partnerId;
    this.getLoansDisbursedByGenderSummary();
    this.getLoansDisbursedByPipelineSummary();
    this.getLoansDisbursedByQualitySummary();
  }

  getLoansDisbursedByGenderSummary() {
    this.dashBoardService.getLoansDisbursedByGenderSummary(this.partnerId)
      .subscribe({
        next: (response) => {
          this.loansDisbursedByGender = response;
        },
        error: (error) => { }
      });
  }

  getLoansDisbursedByPipelineSummary() {
    this.dashBoardService.getLoansDisbursedByPipelineSummary(this.partnerId)
      .subscribe({
        next: (response) => {
          this.loansDisbursedByPipeline = response;
        },
        error: (error) => { }
      });
  }

  getLoansDisbursedByQualitySummary() {
    this.dashBoardService.getLoansDisbursedByQualitySummary(this.partnerId)
      .subscribe({
        next: (response) => {
          this.loansDisbursedByQuality = response;
        },
        error: (error) => { }
      });
  }

  


  public onSelect(event: any) {
    console.log(event);
  }

}
