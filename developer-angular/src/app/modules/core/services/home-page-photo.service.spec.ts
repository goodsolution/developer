import { TestBed } from '@angular/core/testing';

import { HomePagePhotoService } from './home-page-photo.service';

describe('HomePagePhotoService', () => {
  let service: HomePagePhotoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HomePagePhotoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
