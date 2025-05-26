import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { TableComponent } from '../../util/table/table.component';
import { ReservationService } from '../../../services/reservation.service';
import { PaymentService } from '../../../services/payement.service';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';

interface BillData {
  guestName: string;
  phoneNumber: string;
  roomNumber: string;
}

interface Bill {
  billDate: string;
  netAmount: number;
  taxes: number;
  totalAmount: number;
  // Add other bill properties as needed
}

interface TableConfig {
  columns: { header: string; field: string }[];
  data: any[];
}


@Component({
  selector: 'app-bill-form',
  standalone: true,
  imports: [CommonModule,FormsModule,TableComponent],
  templateUrl: './bill-form.component.html',
  styleUrls: ['./bill-form.component.css']
})
export class BillFormComponent implements OnInit {
  billData: BillData = {
    guestName: '',
    phoneNumber: '',
    roomNumber: ''
  };
  bills: Bill[] = [];
 billTableConfig: TableConfig = {
  columns: [
    { header: 'Bill Date', field: 'billDate' },
    { header: 'Net Amount', field: 'netAmount' },
    { header: 'Taxes', field: 'taxes' },
    { header: 'Total Amount', field: 'totalAmount' },
  ],
  data: []
};


  constructor(
    private router: Router,
    private reservationService: ReservationService,
    private paymentService: PaymentService,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.fetchBills();
  }

  fetchBills(): void {
  this.reservationService.getNotPaidBills().subscribe({
    next: (response) => {
      if (Array.isArray(response.data)) {
        const formattedBills = response.data.map((bill: any) => ({
          ...bill,
          billDate: this.getFormattedDate(bill.billDate),
          netAmount: Math.round(bill.netAmount),
          taxes: Math.round(bill.taxes),
          totalAmount: Math.round(bill.totalAmount),
        }));
        this.bills = formattedBills;
        this.billTableConfig.data = formattedBills;
        console.log('---------fetched Bills--', formattedBills);
      } else {
        console.error("Expected array in response.data, got:", response.data);
        this.toastr.error('Unexpected response format while fetching bills');
      }
    },
    error: (error) => {
      console.error("Failed to fetch bills:", error);
      this.router.navigate(['/']);
    }
  });
}


  handleChange(e: any): void {
    const { name, value } = e.target;
    this.billData = {
      ...this.billData,
      [name]: value
    };
  }

  handleSubmit(form: NgForm): void {
    if (form.valid) {
      console.log(this.billData);
      if (!this.validateContactNumber(this.billData.phoneNumber)) {
        this.toastr.error('Enter valid phone number');
        return;
      }

      this.reservationService.generateBill(this.billData).subscribe({
        next: (response) => {
          if (response.data.success) {
            response.data.bill.billDate = this.getFormattedDate(response.data.bill.billDate);
            response.data.bill.netAmount = parseFloat(response.data.bill.netAmount).toFixed(2);
            this.fetchBills();
            this.toastr.success(response.data.message);
          } else {
            this.toastr.error(response.data.message);
          }
          console.log('Response data---', response.data.bill);
          this.billData = {
            guestName: '',
            phoneNumber: '',
            roomNumber: ''
          };
          form.resetForm();
        },
        error: (error) => {
          console.log(error);
          this.router.navigate(['/']);
        }
      });
    }
  }

  getFormattedDate(dateString: string): string {
    const date = new Date(dateString);
    const formattedDate = date.toISOString().split('T')[0];
    return formattedDate;
  }

  validateContactNumber(contactNumber: string): boolean {
    const contactNumberRegex = /^[0-9]{10}$/;
    return contactNumberRegex.test(contactNumber);
  }
}