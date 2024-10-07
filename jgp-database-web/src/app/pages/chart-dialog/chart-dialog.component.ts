import { AfterViewInit, Component, ElementRef, Inject, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-chart-dialog',
  standalone: true,
  imports: [],
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
