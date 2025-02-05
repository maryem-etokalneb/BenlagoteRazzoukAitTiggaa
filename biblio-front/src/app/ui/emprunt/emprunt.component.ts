import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-emprunt',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './emprunt.component.html',
  styleUrls: ['./emprunt.component.css'],
})
export class EmpruntComponent implements OnInit {
  public livres: any[] = []; 
  public userId: string = ''; 
  public returnDate: string = ''; 
  public selectedBookId: number | null = null; 
  public utilisateurEmail : string ='' ;
  public successMessage: string = '';
  public errorMessage: string = '';

  public emprunt = { 
    livreId: this.selectedBookId,
    utilisateurId: this.userId,
    dateEmprunt: '',
    dateRetour: this.returnDate,
    dateRetourReelle :'',
    penalite :0.0,
    retourne :false,
  };

  constructor(private http: HttpClient, private route: ActivatedRoute) {}

  ngOnInit(): void {
    
    this.route.queryParams.subscribe(params => {
      const bookId = params['id'];
      if (bookId) {
        this.selectedBookId = +bookId; 
        
      } 
    });

    const user = localStorage.getItem('user');
    if (user) {
      try {
        const parsedUser = JSON.parse(user);
        if (parsedUser && parsedUser.idUti) {
          this.userId = parsedUser.idUti; 
          this.utilisateurEmail=parsedUser.email;
        } else {
          console.error('L’objet utilisateur dans localStorage ne contient pas idUti !');
        }
      } catch (error) {
        console.error(error);
      }
    } 
  }

  submitEmprunt(): void {
    if (this.selectedBookId && this.userId && this.returnDate) {
      const emprunt = {
        livreId: this.selectedBookId,
        utilisateurId: this.userId,
        dateEmprunt: new Date().toISOString(),
        dateRetourPrevue: this.returnDate,
      };

      this.http.post('http://localhost:9093/emprunt/emprunt', emprunt).subscribe({
        next: (data) => {
          this.successMessage = 'Livre emprunté avec succès !'; 
          this.errorMessage = ''; 
          this.returnDate = '';
          this.selectedBookId = null;
        },
        error: (err) => {
          this.errorMessage = 'Erreur d’emprunt du livre. Veuillez réessayer.'; 
          this.successMessage = ''; 
        },
      });
    } else {
      this.errorMessage = 'Veuillez remplir tous les champs avant de soumettre !';
      this.successMessage = ''; 
    }
  }
}
