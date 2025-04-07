import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';
import { authenticatedGuard } from './guards/authenticated.guard';
import { HomeComponent } from './Home/home.component';
import { RoomDetailComponentComponent } from './Room/room-detail-component/room-detail-component.component';
import { RegisterComponent } from './authentication/register/register.component';
import { JoinRoomComponent } from './Room/join-room/join-room.component';

export const routes: Routes = [
    {
        path: 'login',
        loadComponent: ()=> import('./authentication/login/login.component').then(m=>m.LoginComponent),
        canActivate:[authenticatedGuard]
    },
    {
        path: 'home',
        component:HomeComponent,
        canActivate:[authGuard]
    },
    { 
        path: 'room/:id', 
        component: RoomDetailComponentComponent 
    },
    {
        path:'register',
        component:RegisterComponent
    },
    {
        path:'room/join',
        component:JoinRoomComponent
    }
];
