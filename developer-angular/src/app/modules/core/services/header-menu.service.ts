import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../environments/environment.development";
import {map, Observable} from "rxjs";
import {HeaderMenu, HeaderMenuResponse} from "../../models/header-menu.model";

@Injectable({
  providedIn: 'root'
})
export class HeaderMenuService {

  apiUrl = environment.frontMenuDeveloperEndpoint;
  constructor(private http: HttpClient) { }

  getMenu(): Observable<HeaderMenu[]>{
    return this.http.get<HeaderMenuResponse[]>(`${this.apiUrl}`).pipe(
      map((menusResponse) =>
        menusResponse.map(
          ({name, url, children}) =>
            new HeaderMenu(
              name,
              url,
              children
            )
        )
      )
    )

  };


}
