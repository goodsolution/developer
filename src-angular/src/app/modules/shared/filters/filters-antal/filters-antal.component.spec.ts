import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FiltersAntalComponent } from './filters-antal.component';

describe('FiltersAntalComponent', () => {
  let component: FiltersAntalComponent;
  let fixture: ComponentFixture<FiltersAntalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FiltersAntalComponent]
    });
    fixture = TestBed.createComponent(FiltersAntalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
