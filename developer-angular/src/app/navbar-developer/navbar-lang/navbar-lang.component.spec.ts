import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarLangComponent } from './navbar-lang.component';

describe('NavbarLangComponent', () => {
  let component: NavbarLangComponent;
  let fixture: ComponentFixture<NavbarLangComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NavbarLangComponent]
    });
    fixture = TestBed.createComponent(NavbarLangComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
