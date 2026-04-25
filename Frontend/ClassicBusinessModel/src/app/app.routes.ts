import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./features/pages/landing-page/landing-page.component').then(
        (m) => m.LandingPageComponent
      )
  },
  {
    path: 'login/:moduleId',
    loadComponent: () =>
      import('./features/pages/module-login-page/module-login-page.component').then(
        (m) => m.ModuleLoginPageComponent
      )
  },
  {
    path: 'modules/:moduleId',
    loadComponent: () =>
      import('./features/pages/module-dashboard/module-dashboard.component').then(
        (m) => m.ModuleDashboardComponent
      )
  },
  {
    path: 'modules/:moduleId/:resourceId/:actionId',
    loadComponent: () =>
      import('./features/pages/action-page/action-page.component').then(
        (m) => m.ActionPageComponent
      )
  },
  {
    path: '**',
    redirectTo: ''
  }
];
