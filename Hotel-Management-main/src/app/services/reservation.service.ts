import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private readonly BASE_URL = 'http://localhost:8004/reservation';

  constructor(private http: HttpClient) {}

  addReservation(reservation: any): Observable<any> {
    return this.http.post(`${this.BASE_URL}/addReservation`, reservation);
  }

  updateReservation(reservation: any, id: number): Observable<any> {
    return this.http.put(`${this.BASE_URL}/updateReservation/${id}`, reservation);
  }

  deleteReservation(id: number): Observable<any> {
    return this.http.delete(`${this.BASE_URL}/deleteReservation/${id}`);
  }

  getAvailableRooms(): Observable<any> {
    return this.http.get(`${this.BASE_URL}/getAvailableRooms`);
  }

  getReservationById(reservationId: number): Observable<any> {
    return this.http.get(`${this.BASE_URL}/getReservationById/${reservationId}`);
  }

  getActiveReservations(): Observable<any> {
    return this.http.get(`${this.BASE_URL}/getActiveReservations`);
  }

  generateBill(bill: any): Observable<any> {
    return this.http.post(`${this.BASE_URL}/generateBill`, bill);
  }

  getNotPaidBills(): Observable<any> {
    return this.http.get(`${this.BASE_URL}/getNotPaidBills`);
  }

  setPaidForBill(billId: number): Observable<any> {
    return this.http.post(`${this.BASE_URL}/setPaidForBill/${billId}`, {});
  }

  getReport(days: number): Observable<any> {
    return this.http.get(`${this.BASE_URL}/report/${days}`);
  }

  removeGuest(guestId: number): Observable<any> {
    return this.http.delete(`${this.BASE_URL}/deleteGuest/${guestId}`);
  }

  getPaymentOrderId(amount: number): Observable<any> {
    return this.http.get(`http://localhost:8005/payment/${amount}`);
  }
}
