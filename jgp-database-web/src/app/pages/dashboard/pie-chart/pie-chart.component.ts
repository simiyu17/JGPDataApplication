import { Component, ElementRef, Input, ViewChild, ViewEncapsulation } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { MatCheckboxModule } from '@angular/material/checkbox';

@Component({
  selector: 'app-pie-chart',
  standalone: true,
  imports: [
    FlexLayoutModule,
    MatCardModule,
    NgxChartsModule,
    MatCheckboxModule
  ],
  templateUrl: './pie-chart.component.html',
  styleUrl: './pie-chart.component.scss',
  encapsulation: ViewEncapsulation.None
})
export class PieChartComponent {

  @Input('data') data: any[];
  @Input('showLegend') showLegend: boolean;
  public gradient = true;
  @Input('colorScheme') colorScheme: any;
  @Input('showLabels') showLabels: boolean;
  @Input('explodeSlices') explodeSlices: boolean;
  @Input('doughnut') doughnut: boolean;
  @Input('chartTitle') chartTitle: string;
  @ViewChild('resizedDiv') resizedDiv: ElementRef;
  public previousWidthOfResizedDiv: number = 0;


  public onSelect(event: any) {
    console.log(event);
  }

  ngAfterViewChecked() {
    if (this.previousWidthOfResizedDiv != this.resizedDiv.nativeElement.clientWidth) {
      setTimeout(() => this.data = [...this.data]);
    }
    this.previousWidthOfResizedDiv = this.resizedDiv.nativeElement.clientWidth;
  }
}

