import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ModuleDefinition } from '../../../core/models/module.models';

@Component({
  selector: 'app-module-card',
  imports: [RouterLink],
  templateUrl: './module-card.component.html',
  styleUrl: './module-card.component.css'
})
export class ModuleCardComponent {
  module = input.required<ModuleDefinition>();
  compact = input(false);

  protected endpointCount(module: ModuleDefinition): number {
    return module.resources.reduce((total, resource) => total + resource.actions.length, 0);
  }

  protected compactTitle(module: ModuleDefinition): string {
    switch (module.id) {
      case 'customer':
        return 'Customer & Payments';
      case 'employee':
        return 'Employee & Office';
      case 'orders':
        return 'Orders';
      case 'product':
        return 'Products';
      case 'report':
        return 'Reports & Analytics';
      default:
        return module.tagline;
    }
  }

  protected compactSummary(module: ModuleDefinition): string {
    switch (module.id) {
      case 'customer':
        return 'Manage customers, credit limits, and payment lookup workflows.';
      case 'employee':
        return 'Handle employees, managers, subordinates, offices, and office staff.';
      case 'orders':
        return 'Create, update, search, and track orders and order items.';
      case 'product':
        return 'Maintain products, product lines, and related catalog lookups.';
      case 'report':
        return 'View revenue, sales, exposure, and high-risk customer reports.';
      default:
        return module.description;
    }
  }
}
