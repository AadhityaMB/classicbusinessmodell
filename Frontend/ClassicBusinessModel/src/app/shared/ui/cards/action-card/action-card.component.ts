import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ModuleAction } from '../../../../core/models/module.models';

@Component({
  selector: 'app-action-card',
  imports: [RouterLink],
  templateUrl: './action-card.component.html',
  styleUrl: './action-card.component.css'
})
export class ActionCardComponent {
  moduleId = input.required<string>();
  resourceId = input.required<string>();
  action = input.required<ModuleAction>();
}
