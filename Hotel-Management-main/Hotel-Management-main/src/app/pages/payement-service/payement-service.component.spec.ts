import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PayementServiceComponent } from './payement-service.component';

describe('PayementServiceComponent', () => {
  let component: PayementServiceComponent;
  let fixture: ComponentFixture<PayementServiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PayementServiceComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PayementServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
