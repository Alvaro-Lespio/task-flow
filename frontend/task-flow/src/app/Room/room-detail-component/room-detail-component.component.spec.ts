import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoomDetailComponentComponent } from './room-detail-component.component';

describe('RoomDetailComponentComponent', () => {
  let component: RoomDetailComponentComponent;
  let fixture: ComponentFixture<RoomDetailComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RoomDetailComponentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RoomDetailComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
