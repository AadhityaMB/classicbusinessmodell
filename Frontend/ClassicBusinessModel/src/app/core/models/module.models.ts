export interface ModuleAction {
  id: string;
  label: string;
  description: string;
  endpoint: string;
  method: 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE';
  tone: 'primary' | 'success' | 'warning' | 'neutral';
  sampleBody?: string;
  pathFields?: ActionField[];
  formFields?: ActionField[];
  queryFields?: ActionField[];
  submitLabel?: string;
  successMessage?: string;
  autoLoad?: boolean;
}

export interface FieldValidationRule {
  value: number | string;
  message?: string;
}

export interface FieldValidation {
  required?: string;
  notBlank?: string;
  email?: string;
  integer?: string;
  min?: FieldValidationRule;
  max?: FieldValidationRule;
  minLength?: FieldValidationRule;
  maxLength?: FieldValidationRule;
  pattern?: FieldValidationRule;
}

export interface ActionField {
  key: string;
  label: string;
  type: 'text' | 'number' | 'email' | 'date' | 'textarea' | 'select';
  required?: boolean;
  placeholder?: string;
  step?: string;
  defaultValue?: string;
  validation?: FieldValidation;
  options?: string[];
}

export interface ModuleResource {
  id: string;
  name: string;
  summary: string;
  accent: string;
  actions: ModuleAction[];
}

export interface ModuleCredentials {
  username: string;
  password: string;
}

export interface ModuleDefinition {
  id: string;
  memberName: string;
  moduleName: string;
  tagline: string;
  description: string;
  theme: string;
  gradient: string;
  credentials: ModuleCredentials;
  loginCheckEndpoint: string;
  resources: ModuleResource[];
}
