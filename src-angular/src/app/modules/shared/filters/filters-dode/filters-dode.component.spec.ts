import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FiltersDodeComponent } from './filters-dode.component';

describe('FiltersDodeComponent', () => {
  let component: FiltersDodeComponent;
  let fixture: ComponentFixture<FiltersDodeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FiltersDodeComponent]
    });
    fixture = TestBed.createComponent(FiltersDodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
