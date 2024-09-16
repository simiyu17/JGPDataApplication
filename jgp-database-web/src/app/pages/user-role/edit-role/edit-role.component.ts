import { Component, Inject, OnInit } from '@angular/core';
import { MatTabsModule } from '@angular/material/tabs';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatRadioModule } from '@angular/material/radio';
import { MatNativeDateModule, MatOptionModule } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { MatSelectModule } from '@angular/material/select';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { GlobalService } from '@services/shared/global.service';
import { UserRoleDto } from '../../../dto/UserRoleDto';
import { UserRoleService } from '@services/users/userroles.service';
import { PermissionsService } from '@services/users/permissions.service';

@Component({
  selector: 'app-edit-role',
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
  templateUrl: './edit-role.component.html',
  styleUrl: './edit-role.component.scss'
})
export class EditRoleComponent implements OnInit {

  public userRoleForm: FormGroup;

  allPermissions: any

  constructor(public dialogRef: MatDialogRef<EditRoleComponent>,
    @Inject(MAT_DIALOG_DATA) public userRole: UserRoleDto,
    public fb: FormBuilder, 
    private userRoleServive: UserRoleService,
    private permissionsServive: PermissionsService,
  private gs: GlobalService) {
    this.userRoleForm = this.fb.group({
    id: null,
    roleName: [null, Validators.compose([Validators.required])],
    description: [null, Validators.compose([Validators.required])],
    permissions: [null, Validators.compose([Validators.required])],
    });
    }
    
  ngOnInit(): void {
    this.getAvailablePermissions();
  }

    getAvailablePermissions() {
      this.permissionsServive.getAvailablePermissions()
        .subscribe({
          next: (response) => {
            this.allPermissions = response;
          },
          error: (error) => { }
        });
    }

    onSubmitEditUserRole(): void {
      if (this.userRoleForm.valid) {
        if(this.userRole?.id){
          this.userRoleServive.updateUserRole(this.userRole.id, this.userRoleForm.value)
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
          this.userRoleServive.createUserRole(this.userRoleForm.value)
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
