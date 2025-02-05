import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LivresComponent } from './ui/livres/livres.component';
import { NavbarComponent } from './ui/navbar/navbar.component';
import { HomeComponent } from './ui/home/home.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { EmpruntComponent } from './ui/emprunt/emprunt.component';
import { RouterModule } from '@angular/router';
import { AuthComponent } from './ui/auth/auth.component';
import { RegesterComponent } from './ui/regester/regester.component';
import { EmpruntsComponent } from './ui/emprunts/emprunts.component';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomeComponent,
    AuthComponent,
    RegesterComponent,
  
  
   
    
  ],
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule ,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    EmpruntComponent,
    EmpruntsComponent,
    LivresComponent
    
  ],
 
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
