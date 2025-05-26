import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { DepartmentService } from '../../../services/department.service'; // Adjust the path
import { FormsModule, NgForm } from '@angular/forms';
import { TableComponent } from '../../../components/util/table/table.component'; // Adjust the path
import { NavbarComponent } from '../../../components/util/navbar/navbar.component';
import { ReportComponent } from '../../../components/report/report/report.component';
import { CommonModule } from '@angular/common';
interface Department {
  id: number;
  name: string;
  description: string;
}

@Component({
  selector: 'app-department',
  templateUrl: './department.component.html',
  styleUrls: ['./department.component.css'],
  standalone: true,
  imports: [CommonModule,FormsModule,TableComponent, NavbarComponent, ReportComponent],
})
export class DepartmentComponent implements OnInit {
  name = '';
  description = '';
  items: Department[] = [];
  isFormSubmitted = false;
  isError = false;
  response: any;
  isUpdating = false;
  updatingDepartmentId: number | null = null;

  constructor(
    private router: Router,
    private departmentService: DepartmentService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.fetchDepartments();
  }

  fetchDepartments(): void {
    this.departmentService.getDepartments().subscribe({
      next: (response) => {
        console.log('Departments----------------', response.data);
        this.items = [...response.data];
      },
      error: (error) => {
        this.router.navigate(['/']);
      },
    });
  }

  handleSubmit(form: NgForm): void {
    if (form.valid) {
      if (this.isUpdating && this.updatingDepartmentId) {
        const updatedItem = {
          name: this.name,
          description: this.description,
        };

        this.departmentService
          .updateDepartment(updatedItem, this.updatingDepartmentId)
          .subscribe({
            next: (response) => {
              this.response = response;
              if (response.data.success) {
                this.isFormSubmitted = true;
                this.fetchDepartments();
                this.toastr.success(response.data.message);
              } else {
                this.toastr.error(response.data.message);
                this.isError = true;
              }
              console.log(response.headers);
            },
            error: (error) => {
              this.router.navigate(['/']);
              console.log(error);
              this.isUpdating = false;
              this.updatingDepartmentId = null;
            },
          });

        this.name = '';
        this.description = '';
        this.isUpdating = false;
        this.updatingDepartmentId = null;
      } else {
        const newItem = {
          name: this.name,
          description: this.description,
        };

        this.departmentService.createDepartment(newItem).subscribe({
          next: (response) => {
            this.response = response;
            if (response.data.success) {
              this.isFormSubmitted = true;
              this.fetchDepartments();
              this.toastr.success(response.data.message);
            } else {
              this.toastr.error(response.data.message);
              this.isError = true;
            }
            console.log(response.headers);
          },
          error: (error) => {
            this.router.navigate(['/']);
            console.log(error);
          },
        });

        this.name = '';
        this.description = '';
      }
    }
  }

  handleDelete(id: number): void {
    this.departmentService.deleteDepartment(id).subscribe({
      next: (response) => {
        console.log('Delete Response', response);
        this.fetchDepartments();
        this.toastr.success(response.data.message);
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  handleUpdate(department: Department): void {
    console.log('---------update Department ---', department.id);
    this.name = department.name;
    this.description = department.description;
    this.isUpdating = true;
    this.updatingDepartmentId = department.id;
  }
}