import { Component, OnInit, Input, ViewEncapsulation } from '@angular/core';
import { MenuService } from '@services/menu.service';
import { Settings, SettingsService } from '@services/settings.service';
import { Menu } from '../../../../common/models/menu.model';
import { RouterModule } from '@angular/router';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';

@Component({
  selector: 'app-vertical-menu',
  standalone: true,
  imports: [
    RouterModule,
    FlexLayoutModule,
    MatButtonModule,
    MatIconModule,
    MatTooltipModule
  ],
  templateUrl: './vertical-menu.component.html',
  styleUrls: ['./vertical-menu.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class VerticalMenuComponent implements OnInit {
  @Input('menuItems') menuItems: Menu[];
  @Input('menuParentId') menuParentId: any;
  parentMenu: Array<any>;
  public settings: Settings;
  constructor(public settingsService: SettingsService, public menuService: MenuService) {
    this.settings = this.settingsService.settings;
  }

  ngOnInit() {
    this.parentMenu = this.menuItems.filter(item => item.parentId == this.menuParentId);
  }

  onClick(menuId: any) {
    this.menuService.toggleMenuItem(menuId);
    this.menuService.closeOtherSubMenus(this.menuItems, menuId);
  }

}
