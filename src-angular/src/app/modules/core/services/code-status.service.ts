import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment.development";
import {HttpClient} from "@angular/common/http";
import {Observable, Subject} from "rxjs";
import {SearchResultCode} from "../models/searchResultCode.model";


@Injectable({
  providedIn: 'root'
})
export class CodeStatusService {

  private apiSystemCode = environment.systemCodeEndpoint;
  private contactComponentTrigger = new Subject<void>();
  private homeComponentTrigger: Subject<void> = new Subject<void>();

  constructor(private http: HttpClient) {
  }

  fetchStatusCode(): Observable<SearchResultCode> {
    return this.http.get<SearchResultCode>(`${this.apiSystemCode}`);
  }

  triggerContactComponentLoading() {
    this.contactComponentTrigger.next();
  }

  getContactComponentTrigger() {
    return this.contactComponentTrigger.asObservable();
  }

  getHomeComponentTrigger() {
    return this.homeComponentTrigger.asObservable();
  }
}
