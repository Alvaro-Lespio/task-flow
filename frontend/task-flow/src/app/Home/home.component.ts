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
  editName: string = ''; 
  editPassword: string = '';
  editingRoomId: string | null = null;
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
            console.error('Failed to load rooms', err);
            this.isLoading = false;
         
        }
      })
  }
  deleteRoom(roomCode:string){
    const confirmDelete = confirm('Are you sure you want to delete this room?');
    if (confirmDelete) {
      this.roomService.deleteRoom(roomCode).subscribe({
        next: () => {
          console.log('room deleted successfully');
          this.getAllRooms();
        },
        error: (err) => {
          console.error('Failed to delete task', err);
        }
      });
    }
  }
  updateRoom(room: any) {
    this.editingRoomId = room.roomCode; 
    this.editName = room.name; 
    this.editPassword = room.password || '';
  }

  saveRoom() {
    if (!this.editingRoomId) return;

    const updatedRoom = {
      name: this.editName,
      password: this.editPassword || undefined 
    };
    this.isLoading = true;
    this.roomService.updateRoom(this.editingRoomId, updatedRoom).subscribe({
      next: (response) => {
        this.getAllRooms();
        this.cancelEdit();
      },
      error: (err) => {
        console.error('Room update failed', err);
        this.error = 'Failed to update room';
        this.isLoading = false;
      }
    });
  }

  cancelEdit() {
    this.editingRoomId = null;
    this.editName = '';
    this.editPassword = '';
  }
  viewRoom(roomId: string) {
    this.router.navigate(['/room', roomId]);
  }
  joinRoom(){
    this.router.navigate(['/room/join'])
  }
  logOut(){
    this.authService.logout();
  }
}

