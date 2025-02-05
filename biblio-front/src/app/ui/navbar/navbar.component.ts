import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
@Component({
  selector: 'app-navbar',
  standalone: false,
  
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {
  constructor(private router: Router) {} 
  userName: string | null = null;
  userId: BigInteger | null = null ;
  ngOnInit(): void {
    const user = localStorage.getItem('user');
    if (user) {
      const parsedUser = JSON.parse(user);
      this.userName = parsedUser.name;
      this.userId= parsedUser.idUti; 
    }
  }
  logout(): void {
    localStorage.removeItem('user'); 
    this.userName = null;
    this.userId= null; 
    if (this.router.url.includes('/emprunt')) { 
      this.router.navigate(['/livres']); 
    } else {
      window.location.reload(); 
    }
  }
}
