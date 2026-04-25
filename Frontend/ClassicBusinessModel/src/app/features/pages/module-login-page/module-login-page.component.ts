import { Component, computed, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MODULES, getModuleById } from '../../../core/data/module-data';
import { AuthService } from '../../../core/services/auth.service';
import { AppSidebarComponent } from '../../../shared/layout/app-sidebar/app-sidebar.component';

@Component({
  selector: 'app-module-login-page',
  imports: [FormsModule, RouterLink, AppSidebarComponent],
  templateUrl: './module-login-page.component.html',
  styleUrl: './module-login-page.component.css'
})
export class ModuleLoginPageComponent {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly authService = inject(AuthService);
  private readonly activeModuleId = signal(this.route.snapshot.paramMap.get('moduleId'));

  protected readonly module = computed(() => getModuleById(this.activeModuleId()) ?? null);
  protected readonly modules = [
    ...MODULES.filter((module) => module.id === 'customer'),
    ...MODULES.filter((module) => module.id === 'employee'),
    ...MODULES.filter((module) => module.id === 'orders'),
    ...MODULES.filter((module) => module.id === 'product'),
    ...MODULES.filter((module) => module.id === 'report')
  ];
  protected readonly form = {
    username: '',
    password: ''
  };
  protected readonly errorMessage = signal('');
  protected readonly isSubmitting = signal(false);

  constructor() {
    this.route.paramMap.subscribe((params) => {
      this.activeModuleId.set(params.get('moduleId'));
      this.form.username = '';
      this.form.password = '';
      this.errorMessage.set('');
      this.isSubmitting.set(false);
    });
  }

  protected async login(): Promise<void> {
    const module = this.module();

    if (!module) {
      this.errorMessage.set('Module not found.');
      return;
    }

    const username = this.form.username.trim().toLowerCase();
    const password = this.form.password.trim();

    this.isSubmitting.set(true);
    this.errorMessage.set('');

    try {
      await this.authService.login(module, username, password);
      await this.router.navigate(['/modules', module.id]);
    } catch (error) {
      if (error instanceof HttpErrorResponse && error.status === 401) {
        this.errorMessage.set('Incorrect password.');
      } else {
        this.errorMessage.set('Login failed. Please try again.');
      }
    } finally {
      this.isSubmitting.set(false);
    }
  }

  protected loginTitle(moduleId: string): string {
    switch (moduleId) {
      case 'customer':
        return 'Customer & Payments Module';
      case 'employee':
        return 'Employee & Offices Module';
      case 'orders':
        return 'Orders Module';
      case 'product':
        return 'Product & Product Lines Module';
      case 'report':
        return 'Reports Module';
      default:
        return 'Module';
    }
  }

  protected loginSummary(moduleId: string): string {
    switch (moduleId) {
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
        return '';
    }
  }
}
