import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./features/landing/landing-page.component').then(
        (m) => m.LandingPageComponent
      )
  },
  {
    path: 'login/:moduleId',
    loadComponent: () =>
      import('./features/auth/module-login-page.component').then(
        (m) => m.ModuleLoginPageComponent
      )
  },
  {
    path: 'modules/orders',
    loadComponent: () =>
      import('./features/dashboard/orders-dashboard.component').then(
        (m) => m.OrdersDashboardComponent
      )
  },
  {
    path: 'modules/:moduleId',
    loadComponent: () =>
      import('./features/dashboard/module-dashboard.component').then(
        (m) => m.ModuleDashboardComponent
      )
  },
  {
    path: 'modules/:moduleId/:resourceId/:actionId',
    loadComponent: () =>
      import('./features/actions/action-page.component').then(
        (m) => m.ActionPageComponent
      )
  },
  {
    path: '**',
    redirectTo: ''
  }
];
