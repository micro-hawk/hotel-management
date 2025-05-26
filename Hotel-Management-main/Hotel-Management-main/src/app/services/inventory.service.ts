import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InventoryService {
  private readonly INVENTORY_BASE_URL = 'http://localhost:8002';

  constructor(private http: HttpClient) {}

  // ITEM CONTROLLER
  addItem(item: any): Observable<any> {
    return this.http.post(`${this.INVENTORY_BASE_URL}/addItem`, item);
  }

  updateItem(item: any, id: number | string): Observable<any> {
    return this.http.put(`${this.INVENTORY_BASE_URL}/updateItem/${id}`, item);
  }

  deleteItem(id: number | string): Observable<any> {
    return this.http.delete(`${this.INVENTORY_BASE_URL}/deleteItem/${id}`);
  }

  getItems(): Observable<any> {
    return this.http.get(`${this.INVENTORY_BASE_URL}/getItems`);
  }

  getItemById(id: number | string): Observable<any> {
    return this.http.get(`${this.INVENTORY_BASE_URL}/getItems/${id}`);
  }

  // ROOM CONTROLLER
  addRoom(room: any): Observable<any> {
    return this.http.post(`${this.INVENTORY_BASE_URL}/addRoom`, room);
  }

  updateRoom(room: any, id: number | string): Observable<any> {
    return this.http.put(`${this.INVENTORY_BASE_URL}/updateRoom/${id}`, room);
  }

  deleteRoom(id: number | string): Observable<any> {
    return this.http.delete(`${this.INVENTORY_BASE_URL}/deleteRoom/${id}`);
  }

  getRooms(): Observable<any> {
    return this.http.get(`${this.INVENTORY_BASE_URL}/getAllRooms`);
  }

  getRoomByRoomNumber(roomNumber: string | number): Observable<any> {
    return this.http.get(`${this.INVENTORY_BASE_URL}/getRoomByRoomNumber/${roomNumber}`);
  }

  // STAFF CONTROLLER
  addStaff(staff: any): Observable<any> {
    return this.http.post(`${this.INVENTORY_BASE_URL}/addStaff`, staff);
  }

  updateStaff(staff: any, id: number | string): Observable<any> {
    return this.http.put(`${this.INVENTORY_BASE_URL}/updateStaff/${id}`, staff);
  }

  deleteStaff(id: number | string): Observable<any> {
    return this.http.delete(`${this.INVENTORY_BASE_URL}/deleteStaff/${id}`);
  }

  getAllStaff(): Observable<any> {
    return this.http.get(`${this.INVENTORY_BASE_URL}/getAllStaff`);
  }
}
