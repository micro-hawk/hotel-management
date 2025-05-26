import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ReservationService } from '../../../services/reservation.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface ReportData {
  reservations: number;
  revenue: number;
}

@Component({
  selector: 'app-report',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {
  data: ReportData = { reservations: 0, revenue: 0 };
  days = '30';

  constructor(
    private reservationService: ReservationService,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.fetchReport();
  }

  fetchReport(days: string = this.days): void {
    const numericDays = Number(days);
    this.reservationService.getReport(numericDays).subscribe({
      next: (response) => {
        this.data = response.data;
        console.log('---------fetched report --', response.data);
        this.toastr.success('Report Fetched');
      },
      error: (error) => {
        console.log(error);
        // this.router.navigate(['/']);
      }
    });
  }

  handleFetchReport(): void {
    this.fetchReport(this.days);
  }

  handleDaysChange(e: any): void {
    this.days = e.target.value;
  }
}