import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-regester',
  standalone: false,
  
  templateUrl: './regester.component.html',
  styleUrl: './regester.component.css'
})
export class RegesterComponent {
  user = {
    name: '',
    email: '',
    address:'',
    password: '',
    password_confirmation: ''
  };
  errorMessage: string = '';
  successMessage: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  onSubmit() {
    if (this.user.password !== this.user.password_confirmation) {
      this.errorMessage = "Les mots de passe ne correspondent pas!";
      return;
    }
    this.http.post<any>('http://localhost:9094/utilisateurs', this.user)
      .subscribe(
        (response) => {
          this.successMessage = 'Inscription réussie! Vous pouvez maintenant vous connecter.';
          this.errorMessage = '';
        },
        (error) => {
          this.errorMessage = error.error.message || 'Erreur lors de l\'inscription. Veuillez réessayer.';
          this.successMessage = '';
        }
      );
  }
}
