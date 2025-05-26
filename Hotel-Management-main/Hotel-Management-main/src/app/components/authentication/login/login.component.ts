import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthenticationService } from '../../../services/authentication.service';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';

interface FormData {
  username: string;
  password: string;
}

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  formData: FormData = {
    username: '',
    password: ''
  };
  isFormSubmitted = false;
  isError = false;
  response: any;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService
  ) { }

  handleSubmit(form: NgForm) {
    if (form.valid) {
      console.log('Form submitted with data:', this.formData);
      
      // Log the service call
      console.log('Calling authentication service...');
      
      this.authenticationService.loginUser(this.formData).subscribe({
        next: (response) => {
          console.log("Login API Response:", response);
          this.response = response;

          if (response.success && response.token) {
            console.log('Login successful, setting token and redirecting...');
            this.isFormSubmitted = true;
            localStorage.setItem('token', response.token);
            this.formData = {
              username: '',
              password: ''
            };
            form.resetForm();
            this.router.navigate(['/dashboard']);
          } else {
            console.error('Login failed - Invalid response:', response);
            this.isError = true;
          }
        },
        error: (error) => {
          console.error('Login API Error:', error);
          this.isError = true;
          this.response = error.error || { message: 'Login failed.' };
        }
      });
    } else {
      console.log('Form is invalid:', form.errors);
    }
  }
}