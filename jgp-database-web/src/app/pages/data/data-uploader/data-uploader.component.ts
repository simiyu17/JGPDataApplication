import { Component, OnDestroy } from '@angular/core';
import { ContentHeaderComponent } from '../../../theme/components/content-header/content-header.component';
import { FileUploadComponent } from "../../file-upload/file-upload.component";
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { MatCardModule } from '@angular/material/card';
import { DataUploadService } from '@services/shared/data-upload.service';
import { ReactiveFormsModule, UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { GlobalService } from '@services/shared/global.service';
import { NoPermissionComponent } from '../../errors/no-permission/no-permission.component';
import { AuthService } from '@services/users/auth.service';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-data-uploader',
  standalone: true,
  imports: [
    ContentHeaderComponent,
    FileUploadComponent,
    MatButtonModule,
    MatSelectModule,
    MatCardModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    MatOptionModule,
    NoPermissionComponent
],
  templateUrl: './data-uploader.component.html',
  styleUrl: './data-uploader.component.scss'
})
export class DataUploaderComponent implements OnDestroy {

  bulkImport: any = {};
  template: File;
  bulkImportForm: UntypedFormGroup;
  partnerType: string | undefined = 'NONE';

  private unsubscribe$ = new Subject<void>();
  constructor(
    private dataUploadService: DataUploadService, 
    private gs: GlobalService,
    private formBuilder: UntypedFormBuilder,
    public authService: AuthService){

  }


  createBulkImportForm() {
    this.bulkImportForm = this.formBuilder.group({
      countryId: [''],
      officeId: [''],
      staffId: [''],
      legalForm: ['', Validators.required],
    });
  }

  ngOnInit() {
    this.partnerType = this.authService.currentUser()?.partnerType === '-' ? 'NONE' : this.authService.currentUser()?.partnerType;
    this.createBulkImportForm();
  }

  /**
   * Sets file form control value.
   * @param {any} $event file change event.
   */
  onFileSelect($event: any) {
    if ($event.target.files.length > 0) {
      this.template = $event.target.files[0];
    }
  }

  uploadTemplate() {
    let legalFormType = '';
    /** Only for Client Bulk Imports */
    if(this.authService.currentUser()?.partnerId){
      console.log(this.authService.currentUser()?.partnerId )
      if (this.template.name.toLowerCase().includes('loan')) {
        legalFormType = 'LOAN';
      } else if (this.template.name.toLowerCase().includes('bmo')) {
        legalFormType = 'BMO';
      }else {
        this.gs.openSnackBar('Invalid Template', "Dismiss");
      }
    }else{
      this.gs.openSnackBar('User must be assigned to a patner!!', "Dismiss");
    }

    if('' !== legalFormType){
    this.dataUploadService
      .uploadDataTemplate(this.template, legalFormType)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (response) => {
          this.gs.openSnackBar(response.message, "Dismiss");
          //this.refreshDocuments();
        }
      });
    }
  }

  downloadTemplate() {
    this.dataUploadService.downloadDataTemplate('LOAN_IMPORT_TEMPLATE')
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (response) => {
          console.log(response)
            this.dataUploadService.downloadFileFromAPIResponse(response);
        }
      });
  }

  downloadTemplate2() {
    console.log(this.bulkImportForm.valid)
    if(this.bulkImportForm.valid){
    this.dataUploadService.downloadDataTemplate(this.bulkImportForm.value.legalForm)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (response) => {
            this.dataUploadService.downloadFileFromAPIResponse(response);
        }
      });
    }
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }
}
