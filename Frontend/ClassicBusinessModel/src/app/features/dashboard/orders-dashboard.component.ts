import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ActionCardComponent } from '../../shared/components/action-card/action-card.component';
import { getModuleById } from '../../core/data/module-data';

@Component({
  selector: 'app-orders-dashboard',
  imports: [RouterLink, ActionCardComponent],
  templateUrl: './orders-dashboard.component.html',
  styleUrl: './orders-dashboard.component.css'
})
export class OrdersDashboardComponent {
  protected readonly module = getModuleById('orders')!;
}
