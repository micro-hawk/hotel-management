import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const token = localStorage.getItem('token');
  const router = inject(Router);
  const toastr = inject(ToastrService);

  // Skip token check for authentication endpoints and API requests
  if (req.url.includes('/token') || 
      req.url.includes('/register') || 
      req.url.includes('/inventory') || 
      req.url.includes('/room')) {
    return next(req);
  }

  // If no token is present, redirect to login
  if (!token) {
    router.navigate(['/']);
    return throwError(() => new Error('No authentication token found'));
  }

  // Add both Authorization and CORS headers
  const clonedReq = req.clone({
    headers: req.headers
      .set('Authorization', `Bearer ${token}`)
      .set('Access-Control-Allow-Origin', '*')
      .set('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS')
      .set('Access-Control-Allow-Headers', 'Origin, Content-Type, Accept, Authorization')
  });

  return next(clonedReq).pipe(
    catchError((err) => {
      if (err.status === 401) {
        localStorage.removeItem('token');
        toastr.error('Session expired. Please login again.');
        router.navigate(['/login']);
      } 
      else if (err.status === 403) {
        toastr.error('You do not have permission to access this resource');
        router.navigate(['/dashboard']);
      }
      return throwError(() => err);
    })
  );
};
