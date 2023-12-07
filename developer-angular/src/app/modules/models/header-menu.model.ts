export interface HeaderMenuResponse {
  name: string;
  url: string;
  children: HeaderMenuResponse[];
}

export class HeaderMenu implements HeaderMenuResponse {
  constructor(
    public name: string,
    public url: string,
    public children: HeaderMenu[]
  ) {
  }
}
