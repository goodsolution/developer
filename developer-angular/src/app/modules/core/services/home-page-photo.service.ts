import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment.development";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {HomePagePhotoResponse} from "../../models/home-page-photo.model";

@Injectable({
  providedIn: 'root'
})
export class HomePagePhotoService {

  apiUrl: string = environment.homePagePhoto;

  constructor(private http: HttpClient) {
  }

  getPhoto(): Observable<HomePagePhotoResponse> {
    return this.http.get<HomePagePhotoResponse>(`${this.apiUrl}`)
  }

}
