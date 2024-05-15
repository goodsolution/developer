import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeveloperAntalComponent } from './developer-antal.component';

describe('DeveloperAntalComponent', () => {
  let component: DeveloperAntalComponent;
  let fixture: ComponentFixture<DeveloperAntalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeveloperAntalComponent]
    });
    fixture = TestBed.createComponent(DeveloperAntalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
