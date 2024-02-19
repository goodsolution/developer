import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {firstValueFrom, Observable, of, ReplaySubject, tap} from "rxjs";
import {SearchResultCode} from "../models/searchResultCode.model";
import {ConstantsService} from "./constants.service";


@Injectable({
  providedIn: 'root'
})
export class CodeStatusService {
  private statusCodeSource = new ReplaySubject<SearchResultCode>(1);
  private appConfig: any = {};

  constructor(private http: HttpClient, private constantsService: ConstantsService) {
  }

  async loadAppConfig(): Promise<void> {
    this.appConfig = await firstValueFrom(this.http.get(`${this.constantsService.API_SYSTEM_CODE}`));
  }

  get getSystemCode(): string {
    return this.appConfig?.['system.code'];
  }

  fetchStatusCode(): Observable<SearchResultCode> {
    const statusCode: SearchResultCode = {
      code: this.getSystemCode
    };
    return of(statusCode).pipe(
      tap({
        next: (code) => this.statusCodeSource.next(code),
        error: (error) => console.error('Error fetching status code:', error)
      })
    );
  }

}
