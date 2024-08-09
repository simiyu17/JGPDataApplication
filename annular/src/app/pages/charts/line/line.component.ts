import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { multi, single } from '@data/charts.data';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { ContentHeaderComponent } from '../../../theme/components/content-header/content-header.component';

@Component({
  selector: 'app-line',
  standalone: true,
  imports: [
    FlexLayoutModule,
    MatCardModule,
    NgxChartsModule,
    ContentHeaderComponent
  ],
  templateUrl: './line.component.html'
})
export class LineComponent {
  public single: any[];
  public multi: any[];
  public showXAxis = true;
  public showYAxis = true;
  public gradient = false;
  public showLegend = false;
  public showXAxisLabel = true;
  public xAxisLabel = 'Year';
  public showYAxisLabel = true;
  public yAxisLabel = 'Population';
  public colorScheme: any = {
    domain: ['#2F3E9E', '#D22E2E', '#378D3B', '#0096A6', '#F47B00', '#606060']
  };
  public autoScale = true;

  constructor() {
    Object.assign(this, { single, multi })
  }

  onSelect(event: any) {
    console.log(event);
  }
}
