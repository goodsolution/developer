import { TestBed } from '@angular/core/testing';

import { DynamicComponentLoadingServiceService } from './dynamic-component-loading-service.service';

describe('DynamicComponentLoadingServiceService', () => {
  let service: DynamicComponentLoadingServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DynamicComponentLoadingServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
