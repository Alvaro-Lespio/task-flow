import { ChangeDetectorRef, Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { AuthService } from '../../authentication/services/auth.service';
import { RoomService } from '../../Room/Service/room.service';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-task',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './task.component.html',
  styleUrl: './task.component.css'
})
export class TaskComponent implements OnChanges{
  
  @Input() roomId!: string; 
  @Input() tasks: any[] = [];
  @Output() taskChanged = new EventEmitter<void>(); // Notify parent when task is created

  newTask = {
    title: '',
    description: '',
    finishDate: '',
    difficulty: '', 
    assignedTo: '' 
  };
  editingTask: any | null = null;
  displayFinishDate: string | null = null;
  constructor(
    public authService: AuthService,
    private roomService: RoomService,
    private cdr: ChangeDetectorRef
  ) {}
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['tasks']) {
      console.log('Tasks input changed:', changes['tasks'].currentValue);
      this.tasks = changes['tasks'].currentValue || [];
      this.cdr.detectChanges();
    }
  }

  onCreateTask() {
    const formattedDate = new Date(this.newTask.finishDate).toISOString();
    const taskData: any = {
      title: this.newTask.title,
      description: this.newTask.description,
      finishDate: formattedDate,
      difficulty: this.newTask.difficulty
    };
    if (this.newTask.assignedTo.trim()) {
      taskData.assignedTo = this.newTask.assignedTo;
    }

    this.roomService.createTask(this.roomId, taskData).subscribe({
      next: () => {
        this.resetForm();
        this.taskChanged.emit(); // Notify parent to refresh
      },
      error: (err) => {
        const errorMessage = err.error || 'Failed to create task';
        alert(errorMessage);
        console.error('Failed to create task', err)
      }
    });
  }


  deleteTask(taskId: number) {
    const confirmDelete = confirm('Are you sure you want to delete this task?');
    if (confirmDelete) {
      this.roomService.deleteTask(this.roomId, taskId).subscribe({
        next: () => {
          console.log('Task deleted successfully');
          this.taskChanged.emit(); // Notify parent to refresh
        },
        error: (err) => {
          console.error('Failed to delete task', err);
          this.taskChanged.emit();
        }
      });
    }
  }
  startEditing(task: any) {
    this.editingTask = { ...task };
    if (this.editingTask.finishDate) {
      const date = new Date(this.editingTask.finishDate);
      this.displayFinishDate = `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}T${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}`;
    } else {
      this.displayFinishDate = null;
      }
    }

  onUpdateTask() {
    if (!this.editingTask) return;

    if (this.displayFinishDate) {
      this.editingTask.finishDate = new Date(this.displayFinishDate).toISOString();
    }
    const taskData: any = {
      title: this.editingTask.title,
      description: this.editingTask.description,
      finishDate: this.displayFinishDate,
      difficulty: this.editingTask.difficulty
    };

    if (this.editingTask.assignedTo) {
      taskData.assignedTo = typeof this.editingTask.assignedTo === 'string'
        ? this.editingTask.assignedTo
        : this.editingTask.assignedTo.username;
    }

    this.roomService.updateTask(this.roomId, this.editingTask.id, taskData).subscribe({
      next: () => {
        console.log('Task updated successfully');
        this.editingTask = null;
        this.taskChanged.emit();
      },
      error: (err) => {
        console.error('Failed to update task:', err);
        this.taskChanged.emit();
      }
    });
  }

  cancelEditing() {
    this.editingTask = null;
  }
  resetForm() {
    this.newTask = {
      title: '',
      description: '',
      finishDate: '',
      difficulty: '', 
      assignedTo: ''
    };
  }
  getAssignedToUsername(assignedTo: any['assignedTo']): string {
    if (typeof assignedTo === 'string') {
      return assignedTo;
    }
    return assignedTo?.username || 'Unknown';
  }
}
