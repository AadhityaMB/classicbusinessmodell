import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { ModuleDefinition } from '../models/module.models';

interface StoredSession {
  moduleId: string;
  username: string;
  password: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly storageKey = 'cbm-session';

  get session(): StoredSession | null {
    const raw = sessionStorage.getItem(this.storageKey);
    return raw ? (JSON.parse(raw) as StoredSession) : null;
  }

  get authorizationHeader(): string | null {
    const session = this.session;

    if (!session) {
      return null;
    }

    return `Basic ${btoa(`${session.username}:${session.password}`)}`;
  }

  async login(module: ModuleDefinition, username: string, password: string): Promise<void> {
    this.logout();

    const headers = new HttpHeaders({
      Authorization: `Basic ${btoa(`${username}:${password}`)}`
    });

    await firstValueFrom(this.http.get(module.loginCheckEndpoint, { headers }));

    sessionStorage.setItem(
      this.storageKey,
      JSON.stringify({
        moduleId: module.id,
        username,
        password
      } satisfies StoredSession)
    );
  }

  logout(): void {
    sessionStorage.removeItem(this.storageKey);
  }
}
