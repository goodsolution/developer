import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment.development";
import {HttpClient} from "@angular/common/http";
import {Observable, ReplaySubject, tap} from "rxjs";
import {SearchResultCode} from "../models/searchResultCode.model";


@Injectable({
  providedIn: 'root'
})
export class CodeStatusService {

  private statusCodeSource = new ReplaySubject<SearchResultCode>(1); // 1 is the buffer size

  private apiSystemCode = environment.systemCodeEndpoint;

  constructor(private http: HttpClient) {
  }

  // fetchStatusCode(): Observable<SearchResultCode> {
  //   return this.http.get<SearchResultCode>(`${this.apiSystemCode}`);
  // }

  fetchStatusCode(): Observable<SearchResultCode> {
    return this.http.get<SearchResultCode>(`${this.apiSystemCode}`).pipe(
      tap({
        next: (code) => this.statusCodeSource.next(code),
        error: (error) => console.error('Error fetching status code:', error)
      })
    );
  }



}
