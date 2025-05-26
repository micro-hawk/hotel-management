import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ReservationService } from '../../services/reservation.service';
import { NavbarComponent } from '../../components/util/navbar/navbar.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface Guest {
  id?: number;
  name: string;
  email: string;
  phoneNumber: string;
  address: string;
  gender: string;
}

interface ReservationModel {
  numberOfChildren: number;
  numberOfAdults: number;
  checkInDate: string;
  checkOutDate: string;
  numberOfNights: number;
  roomNumber: string;
  roomType: string;
  guests: Guest[];
}

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  standalone: true,
  imports: [NavbarComponent, CommonModule, FormsModule],
})
export class ReservationComponent implements OnInit {
  reservationData: ReservationModel = {
    numberOfChildren: 0,
    numberOfAdults: 1,
    checkInDate: '',
    checkOutDate: '',
    numberOfNights: 0,
    roomNumber: '',
    roomType: '',
    guests: [],
  };
  guests: Guest[] = [];
  reservations: any[] = []; // Adjust type as needed
  isUpdateMode = false;
  updateReservationId: number | null = null;
  roomNumberSearch = '';

  constructor(
    private router: Router,
    private reservationService: ReservationService,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.fetchReservations();
  }

  getFormattedDate(dateString: string): string {
    const date = new Date(dateString);
    return date.toISOString().split('T')[0];
  }

  fetchReservations(): void {
    this.reservationService.getActiveReservations().subscribe({
      next: (response) => {
        this.reservations = response.data;
        console.log('---------fetched Reservations--', response.data);
      },
      error: (error) => {
        console.log(error);
        this.router.navigate(['/']);
      },
    });
  }

  handleReservationChange(e: any): void {
    const { name, value } = e.target;

    // Update the specific field in reservationData
    this.reservationData = { ...this.reservationData, [name]: value };

    // Handle date changes
    if (name === 'checkInDate' || name === 'checkOutDate') {
      const checkInDate = new Date(this.reservationData.checkInDate);
      const checkOutDate = new Date(this.reservationData.checkOutDate);
      const today = new Date();
      today.setHours(0, 0, 0, 0); // Strip time for accurate date comparison

      // Validate check-in and check-out dates
      if (checkInDate < today) {
        this.toastr.error('Check-in date cannot be in the past');
        return;
      }

      if (checkOutDate < today) {
        this.toastr.error('Check-out date cannot be in the past');
        return;
      }

      if (checkOutDate <= checkInDate) {
        this.toastr.error('Check-out date must be after check-in date');
        this.reservationData.numberOfNights = 0;
        return;
      }

      // Calculate number of nights
      const nights = Math.ceil(
        (checkOutDate.getTime() - checkInDate.getTime()) / (1000 * 60 * 60 * 24)
      );
      this.reservationData.numberOfNights = nights;
    }
  }

  handleGuestChange(index: number, e: any): void {
    const { name, value } = e.target;
    this.guests = this.guests.map((guest, i) =>
      i === index ? { ...guest, [name]: value } : guest
    );
    this.reservationData.guests = this.guests;
  }

  handleAddGuest(): void {
    this.guests = [...this.guests, { name: '', email: '', phoneNumber: '', address: '', gender: '' }];
    this.reservationData.guests = this.guests;
  }

  handleRemoveGuest(index: number, guest: Guest): void {
    this.guests = this.guests.filter((_, i) => i !== index);
    this.reservationData.guests = this.guests;

    if (this.isUpdateMode && guest.id) {
      this.reservationService.removeGuest(guest.id).subscribe({
        next: (response) => {
          this.toastr.success('Guest removed successfully');
        },
        error: (error) => {
          this.toastr.error('Error removing guest');
          console.log(error);
        },
      });
    }

    console.log('Removed Guest--------', guest);
  }

  handleSubmit(): void {
    const isGuestDataValid = this.guests.every((guest) => {
      if (!this.validateEmail(guest.email)) {
        this.toastr.error(`Invalid email for guest: ${guest.name}`);
        return false;
      }
      if (!this.validateContactNumber(guest.phoneNumber)) {
        this.toastr.error(`Invalid phone number for guest: ${guest.name}`);
        return false;
      }
      return true;
    });

    if (!isGuestDataValid) {
      return;
    }

    if (this.reservationData.numberOfNights <= 0) {
      this.toastr.error('Invalid check-in or check-out date');
      return;
    }

    const reservationToSend = { ...this.reservationData, guests: this.guests };

    if (this.isUpdateMode && this.updateReservationId) {
      this.reservationService
        .updateReservation(reservationToSend, this.updateReservationId)
        .subscribe({
          next: (response) => {
            if (response.data.success) {
              this.fetchReservations();
              this.toastr.success(response.data.message);
            } else {
              this.toastr.error(response.data.message);
            }
            console.log(response.headers);
            this.isUpdateMode = false;
            this.updateReservationId = null;
            this.resetForm();
          },
          error: (error) => {
            console.log(error);
            this.isUpdateMode = false;
            this.updateReservationId = null;
            this.router.navigate(['/']);
          },
        });
    } else {
      this.reservationService.addReservation(reservationToSend).subscribe({
        next: (response) => {
          if (response.data.success) {
            this.toastr.success(response.data.message);
            this.fetchReservations();
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

  validateEmail(email: string): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  }

  validateContactNumber(contactNumber: string): boolean {
    const contactNumberRegex = /^[0-9]{10}$/;
    return contactNumberRegex.test(contactNumber);
  }

  handleUpdateReservation(reservation: any, reservationId: number): void {
    this.reservationData = {
      numberOfChildren: reservation.numberOfChildren,
      numberOfAdults: reservation.numberOfAdults,
      checkInDate: this.getFormattedDate(reservation.checkInDate),
      checkOutDate: this.getFormattedDate(reservation.checkOutDate),
      numberOfNights: reservation.numberOfNights,
      roomNumber: reservation.roomNumber,
      roomType: reservation.roomType,
      guests: [...reservation.guests],
    };
    this.guests = [...reservation.guests];
    this.isUpdateMode = true;
    this.updateReservationId = reservationId;
  }

  handleDeleteReservation(reservationId: number): void {
    this.reservationService.deleteReservation(reservationId).subscribe({
      next: (response) => {
        if (response.data.success) {
          this.toastr.success(response.data.message);
          this.fetchReservations();
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
    this.reservationService.getReservationById(Number(this.roomNumberSearch)).subscribe({
      next: (response) => {
        if (response.data.success) {
          this.toastr.success('Reservation fetched Successfully');
          this.reservations = [response.data.reservation];
        } else {
          this.toastr.error('Reservation not found');
          this.fetchReservations(); // Optionally refresh all reservations
        }
        console.log('search result -----------', response.data);
      },
      error: (error) => {
        this.toastr.error('Reservation not found');
        console.log(error);
      },
    });
    this.roomNumberSearch = '';
  }

  resetForm(): void {
    this.reservationData = {
      numberOfChildren: 0,
      numberOfAdults: 1,
      checkInDate: '',
      checkOutDate: '',
      numberOfNights: 0,
      roomNumber: '',
      roomType: '',
      guests: [],
    };
    this.guests = [];
  }
}