import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  registerData = {
    username: '',
    password: ''
  };

  // Error message
  errorMessage: string = '';

  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  onSubmit() {
    const url = 'http://localhost:8080/api/users/register';
    
    this.http.post(url, this.registerData)
      .subscribe({
        next: (response) => {
          console.log('Registration successful', response);
          this.router.navigate(['/login']);
        },
        error: (error) => {
          this.errorMessage = 'Registration failed: ' + (error.error?.message || 'Unknown error');
          console.error('Registration error', error);
        }
      });
  }
}
