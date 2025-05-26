import { Component } from '@angular/core';
import { NavbarComponent } from '../../../components/util/navbar/navbar.component';
import { BillFormComponent } from '../../../components/bill/bill-form/bill-form.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [NavbarComponent,BillFormComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

}
