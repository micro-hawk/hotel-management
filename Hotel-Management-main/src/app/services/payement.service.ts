import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private readonly baseUrl = 'http://localhost:8006';

  constructor(private http: HttpClient) {}

  getPaymentOrderId(amount: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/payment/${amount}`);
  }
}
