import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PremiseDashboardComponent } from './premise-dashboard.component';

describe('PremiseDashboardComponent', () => {
  let component: PremiseDashboardComponent;
  let fixture: ComponentFixture<PremiseDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PremiseDashboardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PremiseDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
