import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {firstValueFrom, Observable, ReplaySubject, tap} from "rxjs";
import {SearchResultCode} from "../models/searchResultCode.model";
import {ConstantsService} from "./constants.service";


@Injectable({
  providedIn: 'root'
})
export class CodeStatusService {
  private statusCodeSource = new ReplaySubject<SearchResultCode>(1);
  private config: any = {};

  constructor(private http: HttpClient, private constantsService: ConstantsService) {
  }

  async loadConfig(): Promise<void> {
    this.config = await firstValueFrom(this.http.get(`${this.constantsService.API_SYSTEM_CODE}`));
  }

  fetchStatusCode(): Observable<SearchResultCode> {
    return this.http.get<SearchResultCode>(this.getStatusCode()).pipe(
      tap({
        next: (code) => this.statusCodeSource.next(code),
        error: (error) => console.error('Error fetching status code:', error)
      })
    );
  }

  private getStatusCode(): string {
    return this.config?.systemCodeEndpoint;
  }

}
