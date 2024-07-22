import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderProjectOverviewComponent } from './header-project-overview.component';

describe('HeaderProjectOverviewComponent', () => {
  let component: HeaderProjectOverviewComponent;
  let fixture: ComponentFixture<HeaderProjectOverviewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HeaderProjectOverviewComponent]
    });
    fixture = TestBed.createComponent(HeaderProjectOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
