import { Component, EventEmitter, Input, Output } from '@angular/core';

export interface PaginationState {
  pageNumber: number;
  pageSize: number;
  totalPages: number;
  totalElements: number;
  numberOfElements: number;
  first: boolean;
  last: boolean;
}

@Component({
  selector: 'app-data-table',
  standalone: true,
  templateUrl: './data-table.html',
  styleUrl: './data-table.css'
})
export class DataTableComponent {
  @Input() tableRows: Record<string, unknown>[] = [];
  @Input() tableColumns: string[] = [];
  @Input() paginationState: PaginationState | null = null;
  @Input() isLoading = false;

  @Output() previousPage = new EventEmitter<void>();
  @Output() nextPage = new EventEmitter<void>();

  formatCell(value: unknown): string {
    if (value === null || value === undefined) {
      return '-';
    }
    if (typeof value === 'object') {
      return JSON.stringify(value);
    }
    return String(value);
  }
}
