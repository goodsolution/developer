import { TestBed } from '@angular/core/testing';

import { FrontMenuService } from './front-menu.service';

describe('FrontMenuService', () => {
  let service: FrontMenuService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FrontMenuService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
