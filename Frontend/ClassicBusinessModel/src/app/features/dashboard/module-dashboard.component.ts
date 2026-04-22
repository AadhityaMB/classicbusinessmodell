import { Component, computed, inject } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { getModuleById } from '../../core/data/module-data';
import { ActionCardComponent } from '../../shared/components/action-card/action-card.component';
import { AppSidebarComponent } from '../../shared/components/app-sidebar/app-sidebar.component';

@Component({
  selector: 'app-module-dashboard',
  imports: [RouterLink, ActionCardComponent, AppSidebarComponent],
  templateUrl: './module-dashboard.component.html',
  styleUrl: './module-dashboard.component.css'
})
export class ModuleDashboardComponent {
  private readonly route = inject(ActivatedRoute);
  protected readonly module = computed(() => getModuleById(this.route.snapshot.paramMap.get('moduleId')) ?? null);
}
