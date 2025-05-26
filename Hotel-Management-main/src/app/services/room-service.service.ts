import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RoomServiceService {
  private readonly BASE_URL = '/room';

  constructor(private http: HttpClient) {}

  addRoomService(roomService: any): Observable<any> {
    return this.http.post(`${this.BASE_URL}/addRoomService`, roomService);
  }

  updateRoomService(roomService: any, id: number): Observable<any> {
    return this.http.put(`${this.BASE_URL}/updateRoomService/${id}`, roomService);
  }

  deleteRoomService(id: number): Observable<any> {
    return this.http.delete(`${this.BASE_URL}/deleteRoomService/${id}`);
  }

  getActiveRoomServices(): Observable<any> {
    return this.http.get(`${this.BASE_URL}/getActiveRoomServices`);
  }

  getActiveRoomServiceByRoomNumber(roomNumber: number, completed: boolean): Observable<any> {
    return this.http.get(`${this.BASE_URL}/getRoomService/${roomNumber}/${completed}`);
  }

  removeOrderedItem(id: number): Observable<any> {
    return this.http.delete(`${this.BASE_URL}/deleteOrderedItemById/${id}`);
  }
}
