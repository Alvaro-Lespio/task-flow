import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../authentication/services/auth.service';
import { RoomService } from '../Service/room.service';
import { ActivatedRoute, Router } from '@angular/router';
import { TaskComponent } from '../../Task/task/task.component';

@Component({
  selector: 'app-room-detail-component',
  standalone: true,
  imports: [FormsModule,CommonModule,TaskComponent],
  templateUrl: './room-detail-component.component.html',
  styleUrl: './room-detail-component.component.css'
})
export class RoomDetailComponentComponent implements OnInit{
  room: any;

  constructor(
    public authService: AuthService,
    private roomService: RoomService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadRoom();
  }

  loadRoom() {
    const roomId = Number(this.route.snapshot.paramMap.get('id'));
    this.roomService.getRoomById(roomId).subscribe({
      next: (room) => {
        this.room = room;
        if (Array.isArray(this.room.tasks)) {
          this.room.tasks = [...this.room.tasks.flat()];
        }
      },
      error: (err) => {
        console.error('Failed to load room', err);
        this.router.navigate(['/room']);
      }
    });
  }

  goBack() {
    this.router.navigate(['/home']);
  }
}
