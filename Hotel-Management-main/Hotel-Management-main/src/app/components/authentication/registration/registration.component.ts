import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthenticationService } from '../../../services/authentication.service';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';

interface FormData {
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
  role: string;
}

@Component({
  selector: 'app-registration',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {
  formData: FormData = {
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
    role: ''
  };
  isFormSubmitted = false;
  isError = false;
  response: any;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService
  ) { }

  handleSubmit(form: NgForm) {
    if (form.valid && this.formData.password === this.formData.confirmPassword) {
      this.authenticationService.registerUser(this.formData)
        .subscribe({
          next: (response: any) => {
            console.log('Full response:', response);
            if (response.success) { // ✅ This is correct now
              this.isFormSubmitted = true;
              this.isError = false;
              this.response = response;
              form.resetForm();
              this.formData = { username: '', email: '', password: '', confirmPassword: '', role: '' };
            } else {
              this.isError = true;
              this.response = response;
            }

          },
          error: (error) => {
            console.log(error);
            this.isError = true;
            this.response = error; // You might want to handle the error response more specifically
          }
        });

      console.log('Form submitted:', this.formData);
    }
  }

  // No need for isValidEmail here as Angular's email validator is used in the template
}