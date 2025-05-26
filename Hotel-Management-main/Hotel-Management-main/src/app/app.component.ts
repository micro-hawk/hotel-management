import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DashboardComponent } from "./pages/dashboard/dashboard/dashboard.component";
import { LoginComponent } from "./components/authentication/login/login.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Hotel-Management-Frontend';
}
