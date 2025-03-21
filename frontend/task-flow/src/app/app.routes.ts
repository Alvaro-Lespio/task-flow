import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: 'login',
        loadComponent: ()=> import('./authentication/login/login.component').then(m=>m.LoginComponent)
    }
];
