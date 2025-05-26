import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { InventoryService } from '../../../services/inventory.service'; // Adjust the path
import { TableComponent } from '../../../components/util/table/table.component'; // Adjust the path
import { NavbarComponent } from '../../../components/util/navbar/navbar.component';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';

interface Staff {
  id: number;
  name: string;
  email: string;
  position: string;
  departmentId: number;
  contactNumber: string;
  workDescription: string;
}

@Component({
  selector: 'app-inventory-staff',
  templateUrl: './inventory-staff.component.html',
  styleUrls: ['./inventory-staff.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule, TableComponent,NavbarComponent]
,
})
export class InventoryStaffComponent implements OnInit {
  staffs: Staff[] = [];
  isUpdating = false;
  updatingId: number | null = null;
  name = '';
  email = '';
  position = '';
  departmentId: number | null = null;
  contactNumber = '';
  workDescription = '';

  constructor(
    private router: Router,
    private inventoryService: InventoryService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.fetchStaffs();
  }

  fetchStaffs(): void {
    this.inventoryService.getAllStaff().subscribe({
      next: (response) => {
        console.log('Staffs----------------', response.data);
        this.staffs = [...response.data];
      },
      error: (error) => {
        console.log('inside fetch------- Staffs ', error);
        // this.router.navigate(['/']);
      },
    });
  }

  handleSubmit(form: NgForm): void {
    if (form.valid) {
      if (!this.validateEmail(this.email)) {
        this.toastr.error('Invalid email address');
        return;
      }

      if (!this.validateContactNumber(this.contactNumber)) {
        this.toastr.error('Invalid contact number');
        return;
      }

      const newStaff = {
        name: this.name,
        email: this.email,
        position: this.position,
        departmentId: this.departmentId !== null ? this.departmentId : 0,
        contactNumber: this.contactNumber,
        workDescription: this.workDescription,
      };

      if (this.isUpdating && this.updatingId) {
        this.inventoryService.updateStaff(newStaff, this.updatingId).subscribe({
          next: (response) => {
            if (response.data.success) {
              this.fetchStaffs();
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
        this.inventoryService.addStaff(newStaff).subscribe({
          next: (response) => {
            if (response.data.success) {
              this.toastr.success(response.data.message);
              this.fetchStaffs();
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
    this.inventoryService.deleteStaff(id).subscribe({
      next: (response) => {
        if (response.data.success) {
          this.toastr.success(response.data.message);
          this.fetchStaffs();
        } else {
          this.toastr.error(response.data.message);
        }
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  handleUpdate(staff: Staff): void {
    console.log('---------update Staff ---', staff.id);
    this.name = staff.name;
    this.email = staff.email;
    this.position = staff.position;
    this.departmentId = staff.departmentId;
    this.contactNumber = staff.contactNumber;
    this.workDescription = staff.workDescription;
    this.isUpdating = true;
    this.updatingId = staff.id;
  }

  validateEmail(email: string): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  }

  validateContactNumber(contactNumber: string): boolean {
    const contactNumberRegex = /^[0-9]{10}$/;
    return contactNumberRegex.test(contactNumber);
  }

  resetForm(): void {
    this.name = '';
    this.email = '';
    this.position = '';
    this.departmentId = null;
    this.contactNumber = '';
    this.workDescription = '';
  }
}