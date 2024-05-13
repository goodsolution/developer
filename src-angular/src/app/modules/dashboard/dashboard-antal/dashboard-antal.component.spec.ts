import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardAntalComponent } from './dashboard-antal.component';

describe('DashboardAntalComponent', () => {
  let component: DashboardAntalComponent;
  let fixture: ComponentFixture<DashboardAntalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DashboardAntalComponent]
    });
    fixture = TestBed.createComponent(DashboardAntalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
