import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { User, UserContacts, UserProfile, UserWork } from '../../../common/models/user.model';
import { MatTabsModule } from '@angular/material/tabs';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatRadioModule } from '@angular/material/radio';
import { DatePipe } from '@angular/common';
import { MatNativeDateModule } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatInputModule } from '@angular/material/input';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { MatSelectModule } from '@angular/material/select';
import { GlobalService } from '@services/global.service';
import { UserService } from '@services/user.service';
import { PartnerDto } from '../../../dto/Partner';
import { PartnerService } from '@services/partners.service';

@Component({
  selector: 'app-user-dialog',
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
    MatCheckboxModule,
    MatSelectModule,
    DatePipe,
    FormsModule
  ],
  templateUrl: './user-dialog.component.html',
  styleUrl: './user-dialog.component.scss'
})
export class UserDialogComponent implements OnInit {
  public createUserForm: FormGroup;
  public passwordHide: boolean = true;

  partners: PartnerDto[] = [];
  constructor(public dialogRef: MatDialogRef<UserDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public user: User,
              public fb: FormBuilder, private gs: GlobalService, 
              private userService: UserService, private partnerService: PartnerService) {
    this.createUserForm = this.fb.group({
      id: null,
      profile: this.fb.group({
        firstName: null,
        lastName: null,
        gender: null,
        image: null
      }),
      work: this.fb.group({
        partnerId: null,
        designation: null
      }),
      contacts: this.fb.group({
        username: null,
        cellPhone: null,
        town: null
      })
    });
  }

  ngOnInit() {
    this.partnerService.getAvailablePartners()
        .subscribe({
          next: (response) => {
            this.partners = response
          },
          error: (error) => {
            this.gs.openSnackBar(`An error occured ${error.error.detail}`, "Dismiss");
          }
        });
    if (this.user) {
      this.createUserForm.setValue(this.user);
    }
    else {
      this.user = new User();
      this.user.profile = new UserProfile();
      this.user.work = new UserWork();
      this.user.contacts = new UserContacts();
    }
  }

  close(): void {
    this.dialogRef.close();
  }


  onSubmitEditUser(): void {
    if (this.createUserForm.valid) {
      if(this.user?.id){
        this.userService.updateUser(this.user.id, this.createUserForm.value)
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
        this.userService.createUser(this.createUserForm.value)
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

}
