import { Component, Input } from '@angular/core';
import { BaseChartDirective } from 'ng2-charts';
import { ChartConfiguration, ChartData, ChartType } from 'chart.js';

@Component({
  selector: 'app-action-chart',
  standalone: true,
  imports: [BaseChartDirective],
  templateUrl: './action-chart.html',
  styleUrl: './action-chart.css'
})
export class ActionChartComponent {
  @Input() chartData: ChartData | null = null;
  @Input() chartOptions: ChartConfiguration['options'] = {};
  @Input() chartType: ChartType = 'bar';
}
