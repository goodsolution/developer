export interface HomePagePhotoResponse {
  url: string;
}

export class HomePagePhoto implements HomePagePhotoResponse {
  constructor(
    public url: string,
  ) {
  }
}
