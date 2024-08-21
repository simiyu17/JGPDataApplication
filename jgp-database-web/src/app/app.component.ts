import { NgClass } from '@angular/common';
import { Component, inject } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { AuthService } from '@services/auth.service';
import { Settings, SettingsService } from '@services/settings.service';
import { LoginComponent } from './components/login/login.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NgClass, LoginComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  settingsService = inject(SettingsService);
  settings: Settings = this.settingsService.settings;

  isUserLoggedIn: boolean = false;

  constructor(private authService: AuthService, private router: Router){

      this.router.events.subscribe((event: any) => {
          this.navigationInterceptor(event);
      })
  }

  private navigationInterceptor(event: Event): void {
    if (event instanceof NavigationEnd) {
      this.isUserLoggedIn = this.authService.isAuthenticated();
    }
   
  }
}