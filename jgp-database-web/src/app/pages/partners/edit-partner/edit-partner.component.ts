import { Component, Inject } from '@angular/core';
import { MatTabsModule } from '@angular/material/tabs';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatRadioModule } from '@angular/material/radio';
import { DatePipe } from '@angular/common';
import { MatNativeDateModule, MatOptionModule } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatInputModule } from '@angular/material/input';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { MatSelectModule } from '@angular/material/select';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PartnerDto } from '../../../dto/Partner';
import { PartnerService } from '@services/partners.service';
import { GlobalService } from '@services/global.service';

@Component({
  selector: 'app-edit-partner',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    FlexLayoutModule,
    MatTabsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatRadioModule,
    MatDialogModule,
    MatButtonModule,
    MatSelectModule,
    MatOptionModule
  ],
  templateUrl: './edit-partner.component.html',
  styleUrl: './edit-partner.component.scss'
})
export class EditPartnerComponent {

  public partnerForm: FormGroup;

  constructor(public dialogRef: MatDialogRef<EditPartnerComponent>,
    @Inject(MAT_DIALOG_DATA) public partner: PartnerDto,
    public fb: FormBuilder, 
    private partnerService: PartnerService,
  private gs: GlobalService) {
    this.partnerForm = this.fb.group({
    id: null,
    partnerName: [null, Validators.compose([Validators.required])],
    type: [null, Validators.compose([Validators.required])],
    });
    }

    onSubmitEditPartner(): void {
      if (this.partnerForm.valid) {
        if(this.partner?.id){
          this.partnerService.updatePartner(this.partner.id, this.partnerForm.value)
          .subscribe({
            next: (response) => {
              this.gs.openSnackBar("Done sucessfully!!", "Dismiss");
              this.dialogRef.close();
            },
            error: (error) => {
              this.gs.openSnackBar(`An error occured ${error.error.detail}`, "Dismiss");
            }
          });
        }else {
          this.partnerService.createPartner(this.partnerForm.value)
          .subscribe({
            next: (response) => {
              this.gs.openSnackBar("Done sucessfully!!", "Dismiss");
              this.dialogRef.close();
            },
            error: (error) => {
              this.gs.openSnackBar(`An error occured ${error.error.detail}`, "Dismiss");
            }
          });
        }
        
      }
    }


    close(): void {
      this.dialogRef.close();
    }
}
