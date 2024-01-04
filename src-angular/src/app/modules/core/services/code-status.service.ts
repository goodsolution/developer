import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment.development";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {SearchResultCode} from "../models/searchResultCode.model";


@Injectable({
  providedIn: 'root'
})
export class CodeStatusService {

  private apiSystemCode = environment.systemCodeEndpoint;

  constructor(private http: HttpClient) {
  }

  getDeveloperStatus():Observable<SearchResultCode> {
    return this.http.get<SearchResultCode>(`${this.apiSystemCode}`);
  }


}
