import { Component, computed, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MODULES } from '../../../core/data/module-data';

@Component({
  selector: 'app-sidebar',
  imports: [RouterLink],
  templateUrl: './app-sidebar.component.html',
  styleUrl: './app-sidebar.component.css'
})
export class AppSidebarComponent {
  activeModuleId = input<string | null>(null);
  linkMode = input<'login' | 'dashboard'>('login');

  protected readonly modules = computed(() => [
    ...MODULES.filter((module) => module.id === 'customer'),
    ...MODULES.filter((module) => module.id === 'employee'),
    ...MODULES.filter((module) => module.id === 'orders'),
    ...MODULES.filter((module) => module.id === 'product'),
    ...MODULES.filter((module) => module.id === 'report')
  ]);

  protected sidebarLabel(moduleId: string): string {
    switch (moduleId) {
      case 'customer':
        return 'Customer & Payments Module';
      case 'employee':
        return 'Employee & Offices Module';
      case 'orders':
        return 'Orders Module';
      case 'product':
        return 'Product & Product Lines Module';
      case 'report':
        return 'Reports Module';
      default:
        return 'Module';
    }
  }

  protected moduleLink(moduleId: string): string[] {
    return this.linkMode() === 'dashboard' ? ['/modules', moduleId] : ['/login', moduleId];
  }
}
