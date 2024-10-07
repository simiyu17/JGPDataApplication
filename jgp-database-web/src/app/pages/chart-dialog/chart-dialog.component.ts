import { AfterViewInit, Component, ElementRef, Inject, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { PieChartComponent } from '../dashboard/pie-chart/pie-chart.component';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-chart-dialog',
  standalone: true,
  imports: [
    PieChartComponent,
    FlexLayoutModule,
    MatCardModule,
    MatIconModule,
    NgxChartsModule,
    MatButtonModule
  ],
  templateUrl: './chart-dialog.component.html',
  styleUrl: './chart-dialog.component.scss'
})
export class ChartDialogComponent implements AfterViewInit {
  @ViewChild('dialogContentContainer', { static: true }) dialogContentContainer!: ElementRef;


  constructor(
    public dialogRef: MatDialogRef<ChartDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngAfterViewInit(): void {
    // Append the original content from the passed `div` to the dialog content container
    this.dialogContentContainer.nativeElement.appendChild(this.data.content);
  }

  onCloseClick(): void {
    this.dialogRef.close();
  }

}
