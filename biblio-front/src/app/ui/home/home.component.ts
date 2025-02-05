import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: false,
  
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
 public livres: any[] = [];
  public genres: string[] = [];
  public titles: string[] = [];
  public authors: string[] = [];
  public searchParams = {
    title: '',
    author: '',
    genre: ''
  };
  constructor(private http : HttpClient ,private router: Router) {

  }
  ngOnInit(): void {
    
    this.http.get<any[]>("http://localhost:9091/livres").subscribe({
        next : data =>{
          this.livres=data;

          this.titles = [...new Set(this.livres.map(livre => livre.title))];
          this.authors = [...new Set(this.livres.map(livre => livre.author))];
          this.genres = [...new Set(this.livres.map(livre => livre.genre))];
        },
        error:err =>{
          console.log(err);
        }
    })
  }

  onSearch(): void {
    this.router.navigate(['/livres'], {
      queryParams: {
        title: this.searchParams.title || '',
        author: this.searchParams.author || '',
        genre: this.searchParams.genre || ''
      }
    });
  }
}
