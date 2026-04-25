import { Component } from '@angular/core';
import { ModuleCardComponent } from '../../../shared/ui/cards/module-card/module-card.component';
import { MODULES } from '../../../core/data/module-data';
import { AppSidebarComponent } from '../../../shared/layout/app-sidebar/app-sidebar.component';

@Component({
  selector: 'app-landing-page',
  imports: [ModuleCardComponent, AppSidebarComponent],
  templateUrl: './landing-page.component.html',
  styleUrl: './landing-page.component.css'
})
export class LandingPageComponent {
  protected readonly modules = [
    ...MODULES.filter((module) => module.id === 'customer'),
    ...MODULES.filter((module) => module.id === 'employee'),
    ...MODULES.filter((module) => module.id === 'orders'),
    ...MODULES.filter((module) => module.id === 'product'),
    ...MODULES.filter((module) => module.id === 'report')
  ];
}
