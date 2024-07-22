import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FooterProjectOverviewComponent } from './footer-project-overview.component';

describe('FooterProjectOverviewComponent', () => {
  let component: FooterProjectOverviewComponent;
  let fixture: ComponentFixture<FooterProjectOverviewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FooterProjectOverviewComponent]
    });
    fixture = TestBed.createComponent(FooterProjectOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
