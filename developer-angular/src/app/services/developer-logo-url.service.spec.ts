import { TestBed } from '@angular/core/testing';

import { DeveloperLogoUrlService } from './developer-logo-url.service';

describe('DeveloperLogoUrlService', () => {
  let service: DeveloperLogoUrlService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeveloperLogoUrlService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
