import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';  // Ensure Router is imported
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-livres',
  templateUrl: './livres.component.html',
  styleUrls: ['./livres.component.css'],
  imports: [CommonModule],
})
export class LivresComponent implements OnInit {
  public livres: any[] = [];
  public genres: string[] = [];
  public titles: string[] = [];
  public authors: string[] = [];
  public searchParams = {
    title: '',
    author: '',
    genre: ''
  };

  constructor(private http: HttpClient, private route: ActivatedRoute, private router: Router) {}  // Inject Router here

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.searchParams.title = params['title'] || '';
      this.searchParams.author = params['author'] || '';
      this.searchParams.genre = params['genre'] || '';
      this.loadLivres(); // Load books based on query parameters
    });
  }

  loadLivres(): void {
    let params = new HttpParams();

    // Add parameters only if they exist
    if (this.searchParams.title) params = params.set('title', this.searchParams.title);
    if (this.searchParams.author) params = params.set('author', this.searchParams.author);
    if (this.searchParams.genre) params = params.set('genre', this.searchParams.genre);

    const searchUrl = params.keys().length
      ? 'http://localhost:9091/livres/search'
      : 'http://localhost:9091/livres';

    this.http.get<any[]>(searchUrl, { params }).subscribe({
      next: (data) => {
        this.livres = data;
      },
      error: (err) => {
        console.error('Error loading books:', err);
      },
    });
  }

  goToEmprunt(bookId: number): void {
    const user = localStorage.getItem('user'); 
    if (user) {
      this.router.navigate(['/emprunt'], { queryParams: { id: bookId } });
    } else {
      this.router.navigate(['/login']); 
    }
  }



  
}
