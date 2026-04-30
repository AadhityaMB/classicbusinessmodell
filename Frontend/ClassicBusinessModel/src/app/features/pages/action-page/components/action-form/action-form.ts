import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActionField } from '../../../../../core/models/module.models';

@Component({
  selector: 'app-action-form',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './action-form.html',
  styleUrl: './action-form.css'
})
export class ActionFormComponent {
  @Input() formTitle = '';
  @Input() pathFields: ActionField[] = [];
  @Input() queryFields: ActionField[] = [];
  @Input() formFields: ActionField[] = [];
  
  @Input() pathValues: Record<string, string> = {};
  @Input() queryValues: Record<string, string> = {};
  @Input() formValues: Record<string, string> = {};
  
  @Input() pathErrors: Record<string, string> = {};
  @Input() queryErrors: Record<string, string> = {};
  @Input() formErrors: Record<string, string> = {};
  
  @Input() isDeleteAction = false;
  @Input() submitLabel = 'Submit';
  @Input() isLoading = false;
  @Input() canLoadData = false;

  @Output() pathChange = new EventEmitter<{key: string, value: string}>();
  @Output() queryChange = new EventEmitter<{key: string, value: string}>();
  @Output() formChange = new EventEmitter<{key: string, value: string}>();
  @Output() loadData = new EventEmitter<void>();
  @Output() submitForm = new EventEmitter<void>();

  onPathChange(key: string, value: string) { this.pathChange.emit({key, value}); }
  onQueryChange(key: string, value: string) { this.queryChange.emit({key, value}); }
  onFormChange(key: string, value: string) { this.formChange.emit({key, value}); }

  fieldPattern(field: ActionField): string | null {
    return field.validation?.pattern ? String(field.validation.pattern.value) : null;
  }
  
  fieldMaxLength(field: ActionField): number | null {
    return field.validation?.maxLength ? Number(field.validation.maxLength.value) : null;
  }
  
  fieldMinLength(field: ActionField): number | null {
    return field.validation?.minLength ? Number(field.validation.minLength.value) : null;
  }
  
  inputMode(field: ActionField): string | null {
    if (field.type === 'email') return 'email';
    if (field.key.toLowerCase().includes('phone')) return 'tel';
    if (field.type === 'number') return 'decimal';
    return null;
  }
}
