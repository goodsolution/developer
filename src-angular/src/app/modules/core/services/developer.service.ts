import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment.development";
import {HttpClient} from "@angular/common/http";
import {SearchResultDeveloperModel} from "../models/searchResultDeveloper.model";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DeveloperService {
  apiUrl = environment.developerBySystemCodeEndpoint;

  constructor(private http: HttpClient) {
  }

  fetchDevelopers(): Observable<SearchResultDeveloperModel> {
    return this.http.get<SearchResultDeveloperModel>(`${this.apiUrl}`);
  }


}
