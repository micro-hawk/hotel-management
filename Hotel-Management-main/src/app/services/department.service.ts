import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {
  private readonly DEPT_BASE_URL = 'http://localhost:8005/department';
  private readonly AUTH_BASE_URL = 'http://localhost:8005/auth';

  constructor(private http: HttpClient) {}

  createDepartment(department: any): Observable<any> {
    return this.http.post(`${this.DEPT_BASE_URL}/save`, department);
  }

  getDepartments(): Observable<any> {
    return this.http.get(`${this.DEPT_BASE_URL}/get`);
  }

  deleteDepartment(id: number | string): Observable<any> {
    return this.http.delete(`${this.DEPT_BASE_URL}/delete/${id}`);
  }

  updateDepartment(department: any, id: number | string): Observable<any> {
    return this.http.put(`${this.DEPT_BASE_URL}/update/${id}`, department);
  }

  adminAccess(): Observable<any> {
    return this.http.get(`${this.AUTH_BASE_URL}/admin`);
  }
}
