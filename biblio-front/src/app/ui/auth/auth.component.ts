import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-auth',
  standalone: false,
  
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.css'
})
export class AuthComponent {
  user = {
    email: '',
    password: ''
  };
  errorMessage = '';

  constructor(private http: HttpClient, private router: Router) {}
  onSubmit() {
    
    this.http.post<any>('http://localhost:9094/utilisateurs/login', this.user)
      .subscribe(
        (response) => {
          localStorage.setItem('user', JSON.stringify(response));
          this.router.navigate(['/']).then(() => {
            window.location.reload();  
          }); 
        },
        (error) => {
          this.errorMessage = 'Email ou mot de passe incorrect';
        }
      );
  }

}
