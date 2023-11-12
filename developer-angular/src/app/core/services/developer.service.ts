import {Injectable} from "@angular/core";
import {catchError, Observable, of, tap} from "rxjs";
import {MessageService} from "./message.service";
import {HttpClient} from "@angular/common/http";
import {SearchResultDevelopers} from "../../shared/interfaces/searchResultDevelopers";
import {environment} from "../../../environments/environment.development";

@Injectable({
  providedIn: 'root'
})
export class DeveloperService {

  private developerUrl = environment.developerEndpoint

  constructor(
    private messageService: MessageService,
    private http: HttpClient,
  ) {
  }

  getDevelopers(): Observable<SearchResultDevelopers> {
    return this.http.get<SearchResultDevelopers>(this.developerUrl)
      .pipe(
        tap(_ => this.log('fetched premises')),
        catchError(this.handleError<SearchResultDevelopers>('getDevelopers', {developers: []}))
      );
  }

  // getPremise(id: number): Observable<SearchResultPremises> {
  //   const url = `${this.developerUrl}/${id}`;
  //   return this.http.get<SearchResultPremises>(url).pipe(
  //     tap(_ => this.log(`fetched premise id=${id}`)),
  //     catchError(this.handleError<SearchResultPremises>(`getPremise id=${id}`))
  //   );
  // }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }

  private log(message: string) {
    this.messageService.add(`DeveloperService: ${message}`);
  }

}
