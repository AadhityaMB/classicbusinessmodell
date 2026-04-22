import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { ModuleAction } from '../models/module.models';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly http = inject(HttpClient);

  async executeAction(
    action: ModuleAction,
    pathValues: Record<string, string>,
    queryValues: Record<string, string>,
    body: Record<string, unknown> | null
  ): Promise<unknown> {
    let url = action.endpoint.replace(/^(GET|POST|PUT|PATCH|DELETE)\s+/, '');

    Object.entries(pathValues).forEach(([key, value]) => {
      url = url.replace(`{${key}}`, encodeURIComponent(value.trim()));
    });

    const searchParams = new URLSearchParams();

    Object.entries(queryValues).forEach(([key, value]) => {
      if (value.trim()) {
        searchParams.set(key, value.trim());
      }
    });

    const normalizedQuery = searchParams.toString();
    if (normalizedQuery) {
      url = `${url}?${normalizedQuery}`;
    }

    const options: {
      body?: unknown;
      headers?: HttpHeaders;
    } = {};

    if (action.method !== 'GET' && action.method !== 'DELETE') {
      options.body = body ?? {};
      options.headers = new HttpHeaders({
        'Content-Type': 'application/json'
      });
    }

    return firstValueFrom(this.http.request(action.method, url, options));
  }
}
