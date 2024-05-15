import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeveloperDodeComponent } from './developer-dode.component';

describe('DeveloperDodeComponent', () => {
  let component: DeveloperDodeComponent;
  let fixture: ComponentFixture<DeveloperDodeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeveloperDodeComponent]
    });
    fixture = TestBed.createComponent(DeveloperDodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
