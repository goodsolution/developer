import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardDodeComponent } from './dashboard-dode.component';

describe('DashboardDodeComponent', () => {
  let component: DashboardDodeComponent;
  let fixture: ComponentFixture<DashboardDodeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DashboardDodeComponent]
    });
    fixture = TestBed.createComponent(DashboardDodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
