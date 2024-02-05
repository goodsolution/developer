import { TestBed } from '@angular/core/testing';

import { CodeStatusService } from './code-status.service';

describe('CodeStatusService', () => {
  let service: CodeStatusService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CodeStatusService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
