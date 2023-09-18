import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeveloperContactComponent } from './developer-contact.component';

describe('DeveloperContactComponent', () => {
  let component: DeveloperContactComponent;
  let fixture: ComponentFixture<DeveloperContactComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeveloperContactComponent]
    });
    fixture = TestBed.createComponent(DeveloperContactComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
