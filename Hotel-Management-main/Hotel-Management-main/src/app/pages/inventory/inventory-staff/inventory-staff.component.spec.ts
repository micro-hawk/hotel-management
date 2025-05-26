import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryStaffComponent } from './inventory-staff.component';

describe('InventoryStaffComponent', () => {
  let component: InventoryStaffComponent;
  let fixture: ComponentFixture<InventoryStaffComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InventoryStaffComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InventoryStaffComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
