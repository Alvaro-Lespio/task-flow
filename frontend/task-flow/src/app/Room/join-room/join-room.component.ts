import { Component } from '@angular/core';
import { RoomService } from '../Service/room.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-join-room',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './join-room.component.html',
  styleUrl: './join-room.component.css'
})
export class JoinRoomComponent {

  roomCode:string="";
  password:string="";
  constructor(private roomService: RoomService, private router: Router) {}
  onSubmit(){
    this.roomService.joinRoom(this.roomCode, this.password)
      .subscribe({
        next: (response) => {
          // On successful join, navigate to the room detail component
          this.router.navigate(['/room', this.roomCode]);
        },
        error: (error) => {
          console.log(error);
        }
  })};
}
