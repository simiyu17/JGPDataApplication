import { Component, OnInit, Input, ViewEncapsulation } from '@angular/core';
import { Menu } from '../../../../common/models/menu.model';
import { Settings, SettingsService } from '@services/settings.service';
import { MenuService } from '@services/menu.service';
import { RouterModule } from '@angular/router';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';

@Component({
  selector: 'app-horizontal-menu',
  standalone: true,
  imports: [
    RouterModule,
    FlexLayoutModule,
    MatButtonModule,
    MatIconModule,
    MatTooltipModule
  ],
  templateUrl: './horizontal-menu.component.html',
  styleUrls: ['./horizontal-menu.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class HorizontalMenuComponent implements OnInit {
  @Input('menuParentId') menuParentId: any;
  public menuItems: Array<Menu>;
  public settings: Settings;
  constructor(public settingsService: SettingsService, public menuService: MenuService) {
    this.settings = this.settingsService.settings;
  }

  ngOnInit() {
    this.menuItems = this.menuService.getHorizontalMenuItems();
    this.menuItems = this.menuItems.filter(item => item.parentId == this.menuParentId);
  }

}