import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private readonly AUTH_BASE_URL = 'http://localhost:8005/auth';

  constructor(private http: HttpClient) {
    console.log('AuthenticationService initialized');
  }

  registerUser(user: any): Observable<any> {
    console.log('Registering user:', user);
    return this.http.post(`${this.AUTH_BASE_URL}/register`, user);
  }

  loginUser(user: any): Observable<any> {
    console.log('Attempting login with:', user);
    return this.http.post(`${this.AUTH_BASE_URL}/token`, user).pipe(
      tap({
        next: (response) => console.log('Login API response:', response),
        error: (error) => console.error('Login API error:', error)
      })
    );
  }
}
