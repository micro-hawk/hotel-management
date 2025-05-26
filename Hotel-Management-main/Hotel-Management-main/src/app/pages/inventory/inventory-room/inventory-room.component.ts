import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { InventoryService } from '../../../services/inventory.service'; 
import { FormsModule, NgForm } from '@angular/forms';
import { TableComponent } from '../../../components/util/table/table.component';
import { NavbarComponent } from '../../../components/util/navbar/navbar.component';
import { CommonModule } from '@angular/common';

interface Room {
  id: number;
  roomNumber: number;
  roomType: string;
  price: number;
  available: boolean;
}

@Component({
  selector: 'app-inventory-room',
  templateUrl: './inventory-room.component.html',
  styleUrls: ['./inventory-room.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule, TableComponent,NavbarComponent]
,
})
export class InventoryRoomComponent implements OnInit {
  rooms: Room[] = [];
  isUpdating = false;
  updatingId: number | null = null;
  roomNumber: number | null = null;
  roomType = '';
  price: number | null = null;
  available = true;
  roomNumberSearch: string = '';

  constructor(
    private router: Router,
    private inventoryService: InventoryService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.fetchRooms();
  }

  fetchRooms(): void {
    this.inventoryService.getRooms().subscribe({
      next: (response) => {
        console.log('Rooms----------------', response.data);
        this.rooms = [...response.data];
      },
      error: (error) => {
        console.log('inside fetch------- Rooms ', error);
        this.router.navigate(['/']);
      },
    });
  }

  handleSubmit(form: NgForm): void {
    if (form.valid) {
      const newRoom = {
        roomNumber: this.roomNumber !== null ? this.roomNumber : 0,
        roomType: this.roomType,
        price: this.price !== null ? this.price : 0,
        available: this.available,
      };

      if (this.isUpdating && this.updatingId) {
        this.inventoryService.updateRoom(newRoom, this.updatingId).subscribe({
          next: (response) => {
            if (response.data.success) {
              this.fetchRooms();
              this.toastr.success(response.data.message);
              this.resetForm();
              this.isUpdating = false;
              this.updatingId = null;
            } else {
              this.toastr.error(response.data.message);
            }
            console.log(response.headers);
          },
          error: (error) => {
            console.log(error);
            this.isUpdating = false;
            this.updatingId = null;
            this.router.navigate(['/']);
          },
        });
      } else {
        this.inventoryService.addRoom(newRoom).subscribe({
          next: (response) => {
            if (response.data.success) {
              this.toastr.success(response.data.message);
              this.fetchRooms();
              this.resetForm();
            } else {
              this.toastr.error(response.data.message);
            }
            console.log(response.headers);
          },
          error: (error) => {
            console.log(error);
            this.router.navigate(['/']);
          },
        });
      }
    }
  }

  handleDelete(id: number): void {
    this.inventoryService.deleteRoom(id).subscribe({
      next: (response) => {
        if (response.data.success) {
          this.toastr.success(response.data.message);
          this.fetchRooms();
        } else {
          this.toastr.error(response.data.message);
        }
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  handleUpdate(room: Room): void {
    console.log('---------update Room ---', room.id);
    this.roomNumber = room.roomNumber;
    this.roomType = room.roomType;
    this.price = room.price;
    this.available = room.available;
    this.isUpdating = true;
    this.updatingId = room.id;
  }

  handleRoomNumberSearch(): void {
    console.log('room number serach ---------', this.roomNumberSearch);
    this.inventoryService.getRoomByRoomNumber(this.roomNumberSearch).subscribe({
      next: (response) => {
        if (response.data.success) {
          this.toastr.success(response.data.message);
          this.rooms = [response.data.room];
        } else {
          this.toastr.error(response.data.message);
          this.fetchRooms(); // Optionally refresh all rooms if not found
        }
      },
      error: (error) => {
        console.log(error);
      },
    });

    this.roomNumberSearch = '';
  }

  resetForm(): void {
    this.roomNumber = null;
    this.roomType = '';
    this.price = null;
    this.available = true;
  }
}