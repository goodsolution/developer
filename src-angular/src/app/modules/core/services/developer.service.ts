import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SearchResultDeveloperModel} from "../models/searchResultDeveloper.model";
import {Observable} from "rxjs";
import {ConstantsService} from "./constants.service";

@Injectable({
  providedIn: 'root'
})
export class DeveloperService {
  apiUrl = this.constantsService.API_DEVELOPER_BY_SYSTEM_CODE_ENDPOINT;

  constructor(private http: HttpClient, private constantsService: ConstantsService) {
  }

  fetchDevelopers(): Observable<SearchResultDeveloperModel> {
    return this.http.get<SearchResultDeveloperModel>(`${this.apiUrl}`);
  }


}
