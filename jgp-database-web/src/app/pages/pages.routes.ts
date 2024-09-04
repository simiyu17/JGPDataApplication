import { Routes } from '@angular/router';
import { PagesComponent } from './pages.component';
import { AuthGuard } from '../util/AuthGuard';

export const routes: Routes = [
  {
    path: '',
    component: PagesComponent,
    children: [
      {
        path: '',
        loadComponent: () => import('./dashboard/dashboard.component').then(c => c.DashboardComponent),
        canActivate: [AuthGuard],
        data: { breadcrumb: 'Dashboard' }
      },
      {
        path: 'participants',
        loadComponent: () => import('./clients/clients.component').then(c => c.ClientsComponent),
        canActivate: [AuthGuard],
        data: { breadcrumb: 'Project Participants' }
      },
      {
        path: 'data-list',
        loadComponent: () => import('./data/data-list/data-list.component').then(c => c.DataListComponent),
        canActivate: [AuthGuard],
        data: { breadcrumb: 'Data Management' }
      },
      {
        path: 'lending-data',
        loadComponent: () => import('./lending-data/lending-data.component').then(c => c.LendingDataComponent),
        canActivate: [AuthGuard],
        data: { breadcrumb: 'Lending Data Management' }
      },
      {
        path: 'users',
        loadComponent: () => import('./users/users.component').then(c => c.UsersComponent),
        canActivate: [AuthGuard],
        data: { breadcrumb: 'Users' }
      },
      {
        path: 'partners',
        loadComponent: () => import('./partners/partners.component').then(c => c.PartnersComponent),
        canActivate: [AuthGuard],
        data: { breadcrumb: 'Partners' }
      },
      {
        path: 'ui',
        loadChildren: () => import('./ui/ui.routes').then(p => p.routes),
        data: { breadcrumb: 'UI' }
      },
      {
        path: 'dynamic-menu',
        loadComponent: () => import('./dynamic-menu/dynamic-menu.component').then(c => c.DynamicMenuComponent),
        data: { breadcrumb: 'Dynamic Menu' }
      },
      {
        path: 'mailbox',
        loadComponent: () => import('./mailbox/mailbox.component').then(c => c.MailboxComponent),
        data: { breadcrumb: 'Mailbox' }
      },
      {
        path: 'chat',
        loadComponent: () => import('./chat/chat.component').then(c => c.ChatComponent),
        data: { breadcrumb: 'Chat' }
      },
      {
        path: 'form-controls',
        loadChildren: () => import('./form-controls/form-controls.routes').then(p => p.routes),
        data: { breadcrumb: 'Form Controls' }
      },
      {
        path: 'tables',
        loadChildren: () => import('./tables/tables.routes').then(p => p.routes),
        data: { breadcrumb: 'Tables' }
      },
      { 
        path: 'profile', 
        loadChildren: () => import('./profile/profile.routes').then(p => p.routes),
        data: { breadcrumb: 'Profile' } 
      },
      {
        path: 'schedule',
        loadComponent: () => import('./schedule/schedule.component').then(c => c.ScheduleComponent),
        data: { breadcrumb: 'Schedule' }
      },
      {
        path: 'maps',
        loadChildren: () => import('./maps/maps.routes').then(p => p.routes),
        data: { breadcrumb: 'Maps' }
      },
      {
        path: 'charts',
        loadChildren: () => import('./charts/charts.routes').then(p => p.routes),
        data: { breadcrumb: 'Charts' }
      },
      {
        path: 'drag-drop',
        loadComponent: () => import('./drag-drop/drag-drop.component').then(c => c.DragDropComponent),
        data: { breadcrumb: 'Drag & Drop' }
      },
      {
        path: 'icons',
        loadComponent: () => import('./icons/icons.component').then(c => c.IconsComponent),
        data: { breadcrumb: 'Icons' }
      },
      {
        path: 'blank',
        loadComponent: () => import('./blank/blank.component').then(c => c.BlankComponent),
        data: { breadcrumb: 'Blank page' }
      },
      {
        path: 'search',
        loadComponent: () => import('./search/search.component').then(c => c.SearchComponent),
        data: { breadcrumb: 'Search' }
      },
      {
        path: 'search/:name',
        loadComponent: () => import('./search/search.component').then(c => c.SearchComponent),
        data: { breadcrumb: 'Search' }
      }
    ]
  }
];