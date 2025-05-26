import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { RoomServiceService as HotelRoomService } from '../../services/room-service.service'; 
import { NavbarComponent } from '../../components/util/navbar/navbar.component'; 
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface OrderedItem {
  id?: number;
  name: string;
  description: string;
  quantity: number;
  price: number;
}

interface RoomServiceModel {
  id?: number;
  guestName: string;
  roomNumber: number;
  orderedItems: OrderedItem[];
  completed: boolean;
}

@Component({
  selector: 'app-room-service',
  templateUrl: './room-service.component.html',
  standalone: true,
  imports: [NavbarComponent, CommonModule, FormsModule],
})
export class RoomServiceComponent implements OnInit {
  guestName = '';
  roomNumber: number | null = null;
  orderedItems: OrderedItem[] = [{ name: '', description: '', quantity: 0, price: 0.0 }];
  completed = false;
  roomServices: RoomServiceModel[] = [];
  isUpdateMode = false;
  updateServiceId: number | null = null;
  roomNumberSearch = '';

  constructor(
    private router: Router,
    private roomService: HotelRoomService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.fetchRoomServices();
  }

  fetchRoomServices(): void {
    this.roomService.getActiveRoomServices().subscribe({
      next: (response) => {
        console.log('---------fetched RoomServices--', response);
        if (response && Array.isArray(response)) {
          this.roomServices = response;
        } else if (response && response.data && Array.isArray(response.data)) {
          this.roomServices = response.data;
        } else {
          this.roomServices = [];
        }
      },
      error: (error) => {
        console.log(error);
        this.roomServices = [];
        // this.router.navigate(['/']);
      },
    });
  }

  handleItemChange(index: number, field: string, value: any): void {
    this.orderedItems = this.orderedItems.map((item, i) =>
      i === index ? { ...item, [field]: value } : item
    );
  }

  handleAddItem(): void {
    this.orderedItems = [
      ...this.orderedItems,
      { name: '', description: '', quantity: 0, price: 0.0 },
    ];
  }

  handleRemoveItem(index: number, item: OrderedItem): void {
    this.orderedItems = this.orderedItems.filter((_, i) => i !== index);

    if (this.isUpdateMode && item.id) {
      this.roomService.removeOrderedItem(item.id).subscribe({
        next: (response) => {
          this.toastr.success('Item removed successfully');
        },
        error: (error) => {
          this.toastr.error('Error removing Item');
          console.log(error);
        },
      });
    }
  }

  handleSubmit(): void {
    const newService: RoomServiceModel = {
      guestName: this.guestName,
      roomNumber: this.roomNumber !== null ? this.roomNumber : 0,
      orderedItems: [...this.orderedItems],
      completed: this.completed,
    };

    console.log('---------------isUpdateMode ', this.isUpdateMode);
    if (this.isUpdateMode && this.updateServiceId) {
      this.roomService
        .updateRoomService(newService, this.updateServiceId)
        .subscribe({
          next: (response) => {
            if (response.data.success) {
              this.fetchRoomServices();
              this.toastr.success(response.data.message);
            } else {
              this.toastr.error(response.data.message);
            }
            console.log(response.headers);
            this.isUpdateMode = false;
            this.updateServiceId = null;
            this.resetForm();
          },
          error: (error) => {
            console.log(error);
            this.isUpdateMode = false;
            this.updateServiceId = null;
            this.router.navigate(['/']);
          },
        });
    } else {
      this.roomService.addRoomService(newService).subscribe({
        next: (response) => {
          if (response.data.success) {
            this.toastr.success(response.data.message);
            this.fetchRoomServices();
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

  handleUpdate(serviceId: number, service: RoomServiceModel): void {
    this.guestName = service.guestName;
    this.roomNumber = service.roomNumber;
    this.orderedItems = [...service.orderedItems];
    this.completed = service.completed;
    this.isUpdateMode = true;
    this.updateServiceId = serviceId;
  }

  handleDelete(serviceId: number): void {
    console.log('Delete service:', serviceId);
    this.roomService.deleteRoomService(serviceId).subscribe({
      next: (response) => {
        if (response.data.success) {
          this.toastr.success(response.data.message);
          this.fetchRoomServices();
        } else {
          this.toastr.error(response.data.message);
        }
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  handleRoomNumberSearch(): void {
    this.roomService
      .getActiveRoomServiceByRoomNumber(Number(this.roomNumberSearch), false)
      .subscribe({
        next: (response) => {
          if (response.data.success) {
            this.toastr.success(response.data.message);
            this.roomServices = [response.data.roomService];
          } else {
            this.toastr.error(response.data.message);
            this.fetchRoomServices(); // Optionally refresh all
          }
        },
        error: (error) => {
          console.log(error);
        },
      });

    this.roomNumberSearch = '';
  }

  resetForm(): void {
    this.guestName = '';
    this.roomNumber = null;
    this.orderedItems = [{ name: '', description: '', quantity: 0, price: 0.0 }];
    this.completed = false;
  }
}