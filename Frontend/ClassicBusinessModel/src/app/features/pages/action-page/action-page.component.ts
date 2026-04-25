import { Component, computed, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ActionField } from '../../../core/models/module.models';
import { getModuleById } from '../../../core/data/module-data';
import { ApiService } from '../../../core/services/api.service';
import { AppSidebarComponent } from '../../../shared/layout/app-sidebar/app-sidebar.component';

type FieldScope = 'path' | 'query' | 'form';

@Component({
  selector: 'app-action-page',
  imports: [RouterLink, FormsModule, AppSidebarComponent],
  templateUrl: './action-page.component.html',
  styleUrl: './action-page.component.css'
})
export class ActionPageComponent {
  private readonly route = inject(ActivatedRoute);
  private readonly apiService = inject(ApiService);
  private readonly routeState = signal({
    moduleId: this.route.snapshot.paramMap.get('moduleId'),
    resourceId: this.route.snapshot.paramMap.get('resourceId'),
    actionId: this.route.snapshot.paramMap.get('actionId')
  });

  protected readonly pathValues = signal<Record<string, string>>({});
  protected readonly queryValues = signal<Record<string, string>>({});
  protected readonly formValues = signal<Record<string, string>>({});
  protected readonly touchedPathFields = signal<Record<string, boolean>>({});
  protected readonly touchedQueryFields = signal<Record<string, boolean>>({});
  protected readonly touchedFormFields = signal<Record<string, boolean>>({});
  protected readonly backendFieldErrors = signal<Record<string, string>>({});
  protected readonly isLoading = signal(false);
  protected readonly errorText = signal('');
  protected readonly successText = signal('');
  protected readonly responseData = signal<unknown>(null);
  protected readonly hasSubmitted = signal(false);

  protected readonly selected = computed(() => {
    const routeState = this.routeState();
    const module = getModuleById(routeState.moduleId);
    const resource = module?.resources.find((item) => item.id === routeState.resourceId);
    const action = resource?.actions.find((item) => item.id === routeState.actionId);

    return module && resource && action ? { module, resource, action } : null;
  });

  protected readonly pathKeys = computed(() => {
    const endpoint = this.selected()?.action.endpoint ?? '';
    return Array.from(endpoint.matchAll(/\{([^}]+)\}/g)).map((match) => match[1]);
  });

  protected readonly pathFields = computed(() => {
    const actionPathFields = this.selected()?.action.pathFields ?? [];

    return this.pathKeys().map((key) => {
      const configured = actionPathFields.find((field) => field.key === key);

      if (configured) {
        return configured;
      }

      const label = this.prettyLabel(key);
      const isNumeric = this.isNumericKey(key);

      return {
        key,
        label,
        type: isNumeric ? 'number' : 'text',
        required: true,
        placeholder: `Enter ${label.toLowerCase()}`,
        validation: {
          required: `${label} is required`,
          ...(isNumeric
            ? {
                integer: `${label} must be a whole number`,
                min: { value: 1, message: `${label} must be at least 1` }
              }
            : {
                notBlank: `${label} is required`
              })
        }
      } satisfies ActionField;
    });
  });

  protected readonly formFields = computed(() => this.selected()?.action.formFields ?? []);
  protected readonly queryFields = computed(() => this.selected()?.action.queryFields ?? []);
  protected readonly isWriteAction = computed(() => ['POST', 'PUT', 'PATCH'].includes(this.selected()?.action.method ?? ''));
  protected readonly isDeleteAction = computed(() => this.selected()?.action.method === 'DELETE');
  protected readonly isGetAction = computed(() => this.selected()?.action.method === 'GET');
  protected readonly isGetAllAction = computed(() => this.selected()?.action.id.startsWith('get-all-') ?? false);
  protected readonly isListPage = computed(() => this.isGetAction() && (this.isGetAllAction() || this.selected()?.action.autoLoad || this.queryFields().length > 0));
  protected readonly isLookupPage = computed(() => this.isGetAction() && !this.isListPage());

  protected readonly canLoadData = computed(() => {
    const item = this.selected();
    if (!item || !this.isWriteAction() || item.action.id.startsWith('create-')) {
      return false;
    }

    return item.resource.actions.some(a =>
      a.method === 'GET' &&
      !a.id.startsWith('get-all-') &&
      a.endpoint.includes('{')
    );
  });

  protected readonly responseSummary = computed(() => {
    const response = this.responseData();

    if (!this.isRecord(response)) {
      return [];
    }

    return Object.entries(response)
      .filter(([key]) => key !== 'data')
      .map(([key, value]) => ({
        key,
        value: this.formatCell(value)
      }));
  });

  protected readonly scalarResult = computed(() => {
    const response = this.responseData();

    if (!this.isRecord(response) || !('data' in response)) {
      return null;
    }

    const data = response['data'];

    if (data === null || data === undefined || Array.isArray(data) || this.isRecord(data)) {
      return null;
    }

    return {
      label: this.selected()?.action.label ?? 'Result',
      value: this.formatCell(data)
    };
  });

  protected readonly tableRows = computed(() => {
    const response = this.responseData();
    const source = this.isRecord(response) && 'data' in response ? response['data'] : response;

    if (Array.isArray(source)) {
      return source.filter((item): item is Record<string, unknown> => this.isRecord(item));
    }

    if (this.isRecord(source) && Array.isArray(source['content'])) {
      return source['content'].filter((item): item is Record<string, unknown> => this.isRecord(item));
    }

    if (this.isRecord(source)) {
      return [source];
    }

    return [];
  });

  protected readonly tableColumns = computed(() => {
    const columns = new Set<string>();

    this.tableRows().forEach((row) => {
      Object.keys(row).forEach((key) => columns.add(key));
    });

    return Array.from(columns);
  });

  protected readonly paginationState = computed(() => {
    const response = this.responseData();
    const source = this.isRecord(response) && 'data' in response ? response['data'] : response;

    if (!this.isRecord(source) || !Array.isArray(source['content'])) {
      return null;
    }

    const pageNumber = typeof source['number'] === 'number' ? source['number'] : null;
    const pageSize = typeof source['size'] === 'number' ? source['size'] : null;
    const totalPages = typeof source['totalPages'] === 'number' ? source['totalPages'] : null;
    const totalElements = typeof source['totalElements'] === 'number' ? source['totalElements'] : null;
    const first = typeof source['first'] === 'boolean' ? source['first'] : pageNumber === 0;
    const last = typeof source['last'] === 'boolean' ? source['last'] : totalPages !== null && pageNumber !== null
      ? pageNumber >= totalPages - 1
      : false;
    const numberOfElements = typeof source['numberOfElements'] === 'number'
      ? source['numberOfElements']
      : Array.isArray(source['content'])
        ? source['content'].length
        : 0;

    if (pageNumber === null || pageSize === null || totalPages === null || totalElements === null) {
      return null;
    }

    return {
      pageNumber,
      pageSize,
      totalPages,
      totalElements,
      numberOfElements,
      first,
      last
    };
  });

  constructor() {
    this.route.paramMap.subscribe((params) => {
      this.routeState.set({
        moduleId: params.get('moduleId'),
        resourceId: params.get('resourceId'),
        actionId: params.get('actionId')
      });

      this.pathValues.set({});
      this.queryValues.set(this.buildDefaultQueryValues(this.queryFields()));
      this.formValues.set({});
      this.touchedPathFields.set({});
      this.touchedQueryFields.set({});
      this.touchedFormFields.set({});
      this.backendFieldErrors.set({});
      this.isLoading.set(false);
      this.errorText.set('');
      this.successText.set('');
      this.responseData.set(null);
      this.hasSubmitted.set(false);

      if (this.selected()?.action.autoLoad) {
        this.submit();
      }
    });
  }

  protected submit(): void {
    const item = this.selected();

    if (!item) {
      return;
    }

    this.hasSubmitted.set(true);
    this.backendFieldErrors.set({});
    this.errorText.set('');
    this.successText.set('');
    this.responseData.set(null);

    if (!this.isValid()) {
      return;
    }

    this.isLoading.set(true);

    this.apiService
      .executeAction(
        item.action,
        this.pathValues(),
        this.queryValues(),
        this.buildBody()
      )
      .then((response) => {
        this.responseData.set(response);
        this.successText.set(item.action.successMessage ?? `${item.action.label} completed successfully`);

        if (item.action.method === 'POST') {
          this.formValues.set({});
          this.touchedFormFields.set({});
          this.hasSubmitted.set(false);
        }
      })
      .catch((error: { error?: unknown; status?: number; message?: string }) => {
        const errorDetails = this.extractErrorDetails(error);

        this.backendFieldErrors.set(errorDetails.fieldErrors);
        this.errorText.set(errorDetails.message);
      })
      .finally(() => {
        this.isLoading.set(false);
      });
  }

  protected loadCurrentData(): void {
    const item = this.selected();
    if (!item) return;

    const getAction = item.resource.actions.find(a =>
      a.method === 'GET' &&
      !a.id.startsWith('get-all-') &&
      a.endpoint.includes('{')
    );

    if (!getAction) {
      this.errorText.set('No lookup action found for this resource.');
      return;
    }

    this.isLoading.set(true);
    this.errorText.set('');
    this.successText.set('');

    this.apiService.executeAction(getAction, this.pathValues(), {}, null)
      .then(response => {
        const data = this.isRecord(response) && 'data' in response ? response['data'] : response;
        if (this.isRecord(data)) {
          const newFormValues = { ...this.formValues() };
          this.formFields().forEach(field => {
            if (data[field.key] !== undefined && data[field.key] !== null) {
              newFormValues[field.key] = String(data[field.key]);
            }
          });
          this.formValues.set(newFormValues);
          this.successText.set('Data loaded successfully. You can now modify the fields below.');
        } else {
          this.errorText.set('No data found for this ID.');
        }
      })
      .catch(() => {
        this.errorText.set('Failed to load current data. Please check the ID.');
      })
      .finally(() => this.isLoading.set(false));
  }

  protected updatePathValue(key: string, value: string): void {
    this.pathValues.update((current) => ({ ...current, [key]: value }));
    this.touchedPathFields.update((current) => ({ ...current, [key]: true }));

    if (this.canLoadData()) {
       if (this.pathKeys().every(k => this.pathValues()[k]?.trim())) {
         this.loadCurrentData();
       }
    }
  }

  protected updateQueryValue(key: string, value: string): void {
    this.queryValues.update((current) => ({ ...current, [key]: value }));
    this.touchedQueryFields.update((current) => ({ ...current, [key]: true }));
  }

  protected updateFormValue(key: string, value: string): void {
    this.formValues.update((current) => ({ ...current, [key]: value }));
    this.touchedFormFields.update((current) => ({ ...current, [key]: true }));
    this.clearBackendFieldError(key);
  }

  protected submitLabel(): string {
    const action = this.selected()?.action;

    if (!action) {
      return 'Submit';
    }

    if (action.submitLabel) {
      return action.submitLabel;
    }

    if (this.isGetAllAction()) {
      return 'Fetch Results';
    }

    if (action.method === 'GET') {
      return 'Search';
    }

    if (action.method === 'DELETE') {
      return 'Delete';
    }

    return 'Submit';
  }

  protected goToPreviousPage(): void {
    const pagination = this.paginationState();

    if (!pagination || pagination.first || this.isLoading()) {
      return;
    }

    this.setQueryValue('page', String(Math.max(0, pagination.pageNumber - 1)));
    this.submit();
  }

  protected goToNextPage(): void {
    const pagination = this.paginationState();

    if (!pagination || pagination.last || this.isLoading()) {
      return;
    }

    this.setQueryValue('page', String(pagination.pageNumber + 1));
    this.submit();
  }

  protected prettyLabel(key: string): string {
    return key
      .replace(/([A-Z])/g, ' $1')
      .replace(/^./, (value) => value.toUpperCase());
  }

  protected fieldPattern(field: ActionField): string | null {
    return field.validation?.pattern ? String(field.validation.pattern.value) : null;
  }

  protected fieldMaxLength(field: ActionField): number | null {
    return field.validation?.maxLength ? Number(field.validation.maxLength.value) : null;
  }

  protected fieldMinLength(field: ActionField): number | null {
    return field.validation?.minLength ? Number(field.validation.minLength.value) : null;
  }

  protected inputMode(field: ActionField): string | null {
    if (field.type === 'email') {
      return 'email';
    }

    if (field.key.toLowerCase().includes('phone')) {
      return 'tel';
    }

    if (field.type === 'number') {
      return 'decimal';
    }

    return null;
  }

  protected formatCell(value: unknown): string {
    if (value === null || value === undefined) {
      return '-';
    }

    if (typeof value === 'object') {
      return JSON.stringify(value);
    }

    return String(value);
  }

  protected formTitle(): string {
    if (this.isWriteAction()) {
      return 'Form';
    }

    if (this.isDeleteAction()) {
      return 'Confirm Delete';
    }

    if (this.isGetAllAction()) {
      return 'Fetch Options';
    }

    if (this.isListPage()) {
      return 'Filters';
    }

    return 'Search';
  }

  protected emptyStateText(): string {
    if (this.hasSubmitted()) {
      const actionLabel = this.selected()?.action.label ?? 'records';

      if (this.pathKeys().length) {
        const detail = this.pathKeys()
          .map((key) => this.pathValues()[key]?.trim())
          .filter((value): value is string => !!value)
          .join(', ');

        return detail
          ? `No data was found for ${detail}.`
          : `No data was found for this ${actionLabel.toLowerCase()} request.`;
      }

      return `No data was returned for ${actionLabel.toLowerCase()}.`;
    }

    if (this.isGetAllAction()) {
      return 'Choose the page and size, then click fetch to load records.';
    }

    return 'Use the action form above to load or save data.';
  }

  protected emptyStateTitle(): string {
    return this.hasSubmitted() ? 'No matching records found' : 'No result yet';
  }

  protected fieldError(scope: FieldScope, key: string): string | null {
    if (scope === 'form') {
      const backendError = this.backendFieldErrors()[key];

      if (backendError) {
        return backendError;
      }
    }

    if (!this.shouldShowFieldError(scope, key)) {
      return null;
    }

    const field = this.findField(scope, key);
    return field ? this.validateField(field, this.getFieldValue(scope, key)) : null;
  }

  protected isFieldInvalid(scope: FieldScope, key: string): boolean {
    return this.fieldError(scope, key) !== null;
  }

  protected isRecord(value: unknown): value is Record<string, unknown> {
    return typeof value === 'object' && value !== null && !Array.isArray(value);
  }

  private buildBody(): Record<string, unknown> | null {
    if (!this.isWriteAction()) {
      return null;
    }

    const body: Record<string, unknown> = {};

    this.formFields().forEach((field) => {
      const value = this.formValues()[field.key];

      if (value === undefined || value === '') {
        return;
      }

      body[field.key] = this.castValue(field, value);
    });

    return body;
  }

  private buildDefaultQueryValues(fields: ActionField[]): Record<string, string> {
    return fields.reduce<Record<string, string>>((defaults, field) => {
      if (field.defaultValue !== undefined) {
        defaults[field.key] = field.defaultValue;
      }

      return defaults;
    }, {});
  }

  private castValue(field: ActionField, value: string): unknown {
    if (field.type === 'number') {
      return Number(value);
    }

    return value;
  }

  private isValid(): boolean {
    const pathErrors = this.pathFields().some((field) => this.validateField(field, this.pathValues()[field.key]) !== null);
    const queryErrors = this.queryFields().some((field) => this.validateField(field, this.queryValues()[field.key]) !== null);
    const formErrors = this.formFields().some((field) => this.validateField(field, this.formValues()[field.key]) !== null);

    return !pathErrors && !queryErrors && !formErrors;
  }

  private validateField(field: ActionField, rawValue: string | undefined): string | null {
    const value = rawValue ?? '';
    const trimmedValue = value.trim();
    const validation = field.validation;
    const isEmpty = trimmedValue === '';
    const requiredMessage = validation?.required ?? validation?.notBlank ?? `${field.label} is required`;

    if ((field.required || validation?.required || validation?.notBlank) && isEmpty) {
      return requiredMessage;
    }

    if (isEmpty) {
      return null;
    }

    if (field.type === 'email' || validation?.email) {
      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

      if (!emailPattern.test(trimmedValue)) {
        return validation?.email ?? `Enter a valid ${field.label.toLowerCase()}`;
      }
    }

    if (field.type === 'number') {
      const numericValue = Number(value);

      if (Number.isNaN(numericValue)) {
        return `${field.label} must be a valid number`;
      }

      if (validation?.integer && !Number.isInteger(numericValue)) {
        return validation.integer;
      }

      if (validation?.min && numericValue < Number(validation.min.value)) {
        return validation.min.message ?? `${field.label} must be at least ${validation.min.value}`;
      }

      if (validation?.max && numericValue > Number(validation.max.value)) {
        return validation.max.message ?? `${field.label} must be at most ${validation.max.value}`;
      }
    }

    if (validation?.minLength && trimmedValue.length < Number(validation.minLength.value)) {
      return validation.minLength.message ?? `${field.label} must be at least ${validation.minLength.value} characters`;
    }

    if (validation?.maxLength && trimmedValue.length > Number(validation.maxLength.value)) {
      return validation.maxLength.message ?? `${field.label} must not exceed ${validation.maxLength.value} characters`;
    }

    if (validation?.pattern) {
      const regex = new RegExp(String(validation.pattern.value));

      if (!regex.test(trimmedValue)) {
        return validation.pattern.message ?? `${field.label} format is invalid`;
      }
    }

    return null;
  }

  private shouldShowFieldError(scope: FieldScope, key: string): boolean {
    const touchedFields = this.getTouchedFields(scope);
    return this.hasSubmitted() || Boolean(touchedFields[key]);
  }

  private getTouchedFields(scope: FieldScope): Record<string, boolean> {
    switch (scope) {
      case 'path':
        return this.touchedPathFields();
      case 'query':
        return this.touchedQueryFields();
      case 'form':
        return this.touchedFormFields();
    }
  }

  private getFieldValue(scope: FieldScope, key: string): string | undefined {
    switch (scope) {
      case 'path':
        return this.pathValues()[key];
      case 'query':
        return this.queryValues()[key];
      case 'form':
        return this.formValues()[key];
    }
  }

  private findField(scope: FieldScope, key: string): ActionField | undefined {
    switch (scope) {
      case 'path':
        return this.pathFields().find((field) => field.key === key);
      case 'query':
        return this.queryFields().find((field) => field.key === key);
      case 'form':
        return this.formFields().find((field) => field.key === key);
    }
  }

  private clearBackendFieldError(key: string): void {
    this.backendFieldErrors.update((current) => {
      if (!(key in current)) {
        return current;
      }

      const next = { ...current };
      delete next[key];
      return next;
    });
  }

  private extractErrorDetails(error: { error?: unknown; status?: number; message?: string }): { fieldErrors: Record<string, string>; message: string } {
    const payload = this.isRecord(error.error) ? error.error : null;
    const data = payload && this.isRecord(payload['data']) ? payload['data'] : null;
    const fieldErrors: Record<string, string> = {};

    if (data) {
      Object.entries(data).forEach(([key, value]) => {
         if (typeof value === 'string') {
           fieldErrors[key] = value;
         }
      });
    }

    const serverMessage = payload && typeof payload['message'] === 'string'
      ? payload['message']
      : error.message ?? 'Request failed.';

    if (Object.keys(fieldErrors).length) {
      return {
        fieldErrors,
        message: `${serverMessage}. Fix the highlighted fields and try again.`
      };
    }

    return {
      fieldErrors,
      message: serverMessage
    };
  }

  private setQueryValue(key: string, value: string): void {
    this.queryValues.update((current) => ({ ...current, [key]: value }));
    this.touchedQueryFields.update((current) => ({ ...current, [key]: true }));
  }

  private isNumericKey(key: string): boolean {
    if (['checkNumber', 'officeCode', 'productCode', 'productLine'].includes(key)) {
      return false;
    }

    return /number|id$/i.test(key);
  }
}
