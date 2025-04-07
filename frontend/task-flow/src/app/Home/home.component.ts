import { Component, OnInit } from '@angular/core';
import { RoomService } from '../Room/Service/room.service';
import { Room } from '../Room/Entitie/room';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../authentication/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [ReactiveFormsModule,FormsModule,CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
  name: string = '';
  password: string = '';
  rooms: any[] = [];
  isLoading: boolean = false;
  error: string | null = null;

  constructor(private roomService: RoomService,public authService:AuthService,private router: Router){}

  ngOnInit(): void {
    if(this.authService.isLoggedIn()){
      this.getAllRooms();
    }
  }

  createRoom() {
    this.roomService.createRoom(this.name, this.password)
      .subscribe({
        next: (response) => {
          console.log('Room created', response);
          this.getAllRooms(); 
          this.name = ''; 
          this.password = '';
        },
        error: (err) => {
          console.error('Room creation failed', err),
          this.error = 'Failed to create room'
        }
      });
  }
  getAllRooms(){
    this.roomService.getRooms()
    .subscribe(
      {
        next: (rooms) => {
          this.rooms = rooms,
          this.isLoading = false;
        },
        error: (err) => {
          this.error = 'Failed to load rooms';
          this.isLoading = false;
          console.error('Failed to load rooms', err);
        }
      })
  }
  viewRoom(roomId: string) {
    this.router.navigate(['/room', roomId]);
  }
}
