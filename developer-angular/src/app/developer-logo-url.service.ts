import {Injectable} from '@angular/core';
import {MessageService} from "./message.service";
import {HttpClient} from "@angular/common/http";
import {catchError, Observable, of, tap} from "rxjs";
import {DeveloperLogoUrl} from "./developerLogoUrl";

@Injectable({
  providedIn: 'root'
})
export class DeveloperLogoUrlService {

  private logoUrl = 'https://localhost:8081/api/dev/developers/1/logo';

  constructor(
    private messageService: MessageService,
    private http: HttpClient,
  ) {
  }

  getLogoUrl(): Observable<DeveloperLogoUrl> {
    return this.http.get<DeveloperLogoUrl>(this.logoUrl)
      .pipe(
        tap(_ => this.log('fetched logo')),
        catchError(this.handleError<DeveloperLogoUrl>('getLogo', {url: String}))
      );
  }

  private handleError<T>(operation = 'operation', result?: { url: StringConstructor }) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }

  private log(message: string) {
    this.messageService.add(`DeveloperLogoUrl: ${message}`);
  }

}
