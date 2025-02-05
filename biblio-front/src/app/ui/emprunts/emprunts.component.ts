import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-emprunts',
  templateUrl: './emprunts.component.html',
  styleUrls: ['./emprunts.component.css'],
  imports: [CommonModule],
})
export class EmpruntsComponent implements OnInit {
  emprunts: any[] = [];
  userId: BigInteger | null = null;
  errorMessage: string | null = null;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    const user = localStorage.getItem('user');
    if (user) {
      const parsedUser = JSON.parse(user);
      this.userId = parsedUser.idUti;
      console.log(this.userId );
      if (this.userId) {
        this.loadUserEmprunts(this.userId);
      }
    }
  }

  loadUserEmprunts(userId: BigInteger): void {
    this.http
      .get<any[]>(`http://localhost:9093/emprunt/emprunts/${userId}`)
      .subscribe({
        next: (data) => {
          this.emprunts = data;
          this.errorMessage = null; 
        },
        error: (err) => {
          console.error('Error loading emprunts:', err);
          this.errorMessage =
            err.error.message || 'Une erreur est survenue lors du chargement des emprunts.';
        },
      });
  }
}
