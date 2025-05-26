import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryRoomComponent } from './inventory-room.component';

describe('InventoryRoomComponent', () => {
  let component: InventoryRoomComponent;
  let fixture: ComponentFixture<InventoryRoomComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InventoryRoomComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InventoryRoomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
