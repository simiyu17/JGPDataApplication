import { AfterViewChecked, AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { customers, orders, products, refunds } from '@data/dashboard-data';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { PieChartComponent } from "../pie-chart/pie-chart.component"; 
import { DiskSpaceComponent } from "../disk-space/disk-space.component";
import { multi, single } from '@data/charts.data';
import { DashboardService } from '@services/dashboard/dashboard.service';
import { Subject, takeUntil } from 'rxjs';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { ChartDialogComponent } from '../../chart-dialog/chart-dialog.component';
import { KenyanMapComponent } from "../kenyan-map/kenyan-map.component";

@Component({
  selector: 'app-info-cards',
  standalone: true,
  imports: [
    FlexLayoutModule,
    MatCardModule,
    MatIconModule,
    NgxChartsModule,
    PieChartComponent,
    DiskSpaceComponent,
    MatDialogModule,
    KenyanMapComponent
],
  templateUrl: './info-cards.component.html',
  styleUrl: './info-cards.component.scss'
})
export class InfoCardsComponent implements OnInit, AfterViewChecked, OnDestroy {
  public orders: any[];
  public products: any[];
  public customers: any[];
  public refunds: any[];
  public colorScheme: any = {
    domain: ['rgba(255,255,255,0.8)']
  };
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

  public loansDisbursedByStatus: any[];
  public loansDisbursedByStatusShowXAxis: boolean = true;
  public loansDisbursedByStatusShowYAxis: boolean = true;
  public loansDisbursedByStatusShowLegend: boolean = false;
  public loansDisbursedByStatusShowXAxisLabel: boolean = true;
  public loansDisbursedByStatusShowYAxisLabel: boolean = true;
  public loansDisbursedByStatusXAxisLabel: string = 'Status';
  public loansDisbursedByStatusYAxisLabel: string = 'Amount Disbursed';
  public loansDisbursedByStatusChartTitle: string = 'Loan Disbursed by Pipeline Source';

  public businessesTainedByGender: any[];
  public businessesTainedByGenderShowLegend: boolean = false;
  public businessesTainedByGenderShowLabels: boolean = true;
  public businessesTainedByGenderExplodeSlices: boolean = false;
  public businessesTainedByGenderDoughnut: boolean = true;
  public businessesTainedByGenderChartTitle: string = 'Business Trained By Gender';

  public gradient = false;

  public single: any[];
  public multi: any[];
  public TANeedsByGender: any[]
  public TANeedsByGenderShowXAxis = true;
  public TANeedsByGenderShowYAxis = true;
  public TANeedsByGenderShowLegend = false;
  public TANeedsByGenderShowXAxisLabel = true;
  public TANeedsByGenderXAxisLabel = 'TA Needs';
  public TANeedsByGenderShowYAxisLabel = true;
  public TANeedsByGenderYAxisLabel = 'Number Of Participants';
  public TANeedsByGenderChartTitle: string = 'TA Needs By Gender';

 
  public trainingByPartnerByGender: any[]
  public trainingByPartnerByGenderShowXAxis = true;
  public trainingByPartnerByGenderShowYAxis = true;
  public trainingByPartnerByGenderShowLegend = false;
  public trainingByPartnerByGenderShowXAxisLabel = true;
  public trainingByPartnerByGenderXAxisLabel = 'Partners';
  public trainingByPartnerByGenderShowYAxisLabel = true;
  public trainingByPartnerByGenderYAxisLabel = 'Number';
  public trainingByPartnerByGenderChartTitle: string = 'Training By Partner By Gender';


  public taTrainedBySector: any[];
  public taTrainedBySectorShowXAxis: boolean = true;
  public taTrainedBySectorShowYAxis: boolean = true;
  public taTrainedBySectorShowLegend: boolean = false;
  public taTrainedBySectorShowXAxisLabel: boolean = true;
  public taTrainedBySectorShowYAxisLabel: boolean = true;
  public taTrainedBySectorXAxisLabel: string = 'Industry Sectors';
  public taTrainedBySectorYAxisLabel: string = 'Number Of Participants';
  public taTrainedBySectorChartTitle: string = 'TA Training By Industry Sector';

  public accessedVSOutStandingAmount: any[]
  public accessedVSOutStandingAmountShowXAxis = true;
  public accessedVSOutStandingAmountShowYAxis = true;
  public accessedVSOutStandingAmountShowLegend = false;
  public accessedVSOutStandingAmountShowXAxisLabel = true;
  public accessedVSOutStandingAmountXAxisLabel = 'Partners';
  public accessedVSOutStandingAmountShowYAxisLabel = true;
  public accessedVSOutStandingAmountYAxisLabel = 'Amount';
  public accessedVSOutStandingAmountChartTitle: string = 'Accessed Vs OutStanding By Partner';


  private unsubscribe$ = new Subject<void>();

  @ViewChild('contentDiv', { static: true }) contentDiv!: ElementRef;

  constructor(private dashBoardService: DashboardService, public dialog: MatDialog){
    Object.assign(this, { single, multi });
  }

  ngOnInit() {
    this.orders = orders;
    this.products = products;
    this.customers = customers;
    this.refunds = refunds;
    this.orders = this.addRandomValue('orders');
    this.customers = this.addRandomValue('customers');
    this.getLoansDisbursedByGenderSummary();
    this.getLoansDisbursedByPipelineSummary();
    this.getBusinessesTrainedByGenderSummary();
    this.getLoansDisbursedByStatusSummary();
    this.getTaNeedsByGenderSummary();
    this.getTaTrainingBySectorSummary();
    this.getTrainingByPartnerByGenderSummary();
    this.getLoansAccessedVsOutStandingByPartnerSummary();
  }

  getLoansDisbursedByGenderSummary() {
    this.dashBoardService.getLoansDisbursedByGenderSummary()
    .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (response) => {
          this.loansDisbursedByGender = response;
        },
        error: (error) => { }
      });
  }

  getLoansDisbursedByPipelineSummary() {
    this.dashBoardService.getLoansDisbursedByPipelineSummary()
    .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (response) => {
          this.loansDisbursedByPipeline = response;
        },
        error: (error) => { }
      });
  }

  getLoansDisbursedByStatusSummary() {
    this.dashBoardService.getLoansDisbursedByStatusSummary()
    .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (response) => {
          this.loansDisbursedByStatus = response;
        },
        error: (error) => { }
      });
  }

  getBusinessesTrainedByGenderSummary() {
    this.dashBoardService.getBusinessesTrainedByGenderSummary()
    .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (response) => {
          this.businessesTainedByGender = response;
        },
        error: (error) => { }
      });
  }

  getTaNeedsByGenderSummary() {
    this.dashBoardService.getTaNeedsByGenderSummary()
    .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (response) => {
          this.TANeedsByGender = response;
        },
        error: (error) => { }
      });
  }

  getTaTrainingBySectorSummary() {
    this.dashBoardService.getTaTrainingBySectorSummary()
    .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (response) => {
          this.taTrainedBySector = response;
        },
        error: (error) => { }
      });
  }

  getTrainingByPartnerByGenderSummary() {
    this.dashBoardService.getTrainingByPartnerByGenderSummary()
    .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (response) => {
          this.trainingByPartnerByGender = response;
        },
        error: (error) => { }
      });
  }

  getLoansAccessedVsOutStandingByPartnerSummary() {
    this.dashBoardService.getLoansAccessedVsOutStandingByPartnerSummary()
    .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (response) => {
          this.accessedVSOutStandingAmount = response;
        },
        error: (error) => { }
      });
  }


  public onSelect(event: any) {
    console.log(event);
  }

  public addRandomValue(param: string) {
    switch (param) {
      case 'orders':
        for (let i = 1; i < 30; i++) {
          this.orders[0].series.push({ "name": 1980 + i, "value": Math.ceil(Math.random() * 1000000) });
        }
        return this.orders;
      case 'customers':
        for (let i = 1; i < 15; i++) {
          this.customers[0].series.push({ "name": 2000 + i, "value": Math.ceil(Math.random() * 1000000) });
        }
        return this.customers;
      default:
        return this.orders;
    }
  }

  ngOnDestroy() {
    this.orders[0].series.length = 0;
    this.customers[0].series.length = 0;
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  ngAfterViewChecked() {
    if (this.previousWidthOfResizedDiv != this.resizedDiv.nativeElement.clientWidth) {
      setTimeout(() => this.orders = [...orders]);
      setTimeout(() => this.products = [...products]);
      setTimeout(() => this.customers = [...customers]);
      setTimeout(() => this.refunds = [...refunds]);
    }
    this.previousWidthOfResizedDiv = this.resizedDiv.nativeElement.clientWidth;
  }

  openExpandedChartDialog(): void {
    const contentDivClone = this.contentDiv.nativeElement.cloneNode(true);
    // Dynamically calculate dialog size
    const dialogWidth = window.innerWidth;
    const dialogHeight = window.innerHeight;
    const dialogRef = this.dialog.open(ChartDialogComponent, {
      width: `${dialogWidth}px`,
      height: `${dialogHeight}px`,
      data: { content: contentDivClone },
      panelClass: 'custom-dialog-container', // Custom styles can be added
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Dialog was closed');
    });
  }
  
}