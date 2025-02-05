import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LivresComponent } from './ui/livres/livres.component';
import { HomeComponent } from './ui/home/home.component';
import { EmpruntComponent } from './ui/emprunt/emprunt.component';
import { AuthComponent } from './ui/auth/auth.component';
import { RegesterComponent } from './ui/regester/regester.component';
import { EmpruntsComponent } from './ui/emprunts/emprunts.component';



const routes: Routes = [
  { path: '', component: HomeComponent }, 
  { path: 'livres', component: LivresComponent },
  { path: 'emprunt', component: EmpruntComponent },
  { path: 'login', component: AuthComponent },
  { path: 'regester', component: RegesterComponent },
  { path: 'emprunts', component: EmpruntsComponent }
  



];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
