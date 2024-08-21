import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { Settings, SettingsService } from '@services/settings.service';
import { emailValidator } from '../../theme/utils/app-validators';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { ContentHeaderComponent } from '../../theme/components/content-header/content-header.component';
import { MatInputModule } from '@angular/material/input';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    RouterModule,
    FlexLayoutModule,
    MatSidenavModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatSlideToggleModule,
    ContentHeaderComponent
  ],
  templateUrl: './login.component.html'
})
export class LoginComponent {
  public form: FormGroup;
  public settings: Settings;
  constructor(public settingsService: SettingsService, public fb: FormBuilder, public router: Router) {
    this.settings = this.settingsService.settings;
    this.form = this.fb.group({
      'email': [null, Validators.compose([Validators.required, emailValidator])],
      'password': [null, Validators.compose([Validators.required, Validators.minLength(6)])],
      'rememberMe': false
    });
  }

  public onSubmit(values: Object): void {
    if (this.form.valid) {
      this.router.navigate(['/']);
    }
  }

  ngAfterViewInit() {
    setTimeout(() => {
      this.settings.loadingSpinner = false;
    });
  }
}