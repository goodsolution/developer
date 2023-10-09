import {Injectable} from "@angular/core";
import {catchError, Observable, of, tap} from "rxjs";
import {MessageService} from "./message.service";
import {HttpClient} from "@angular/common/http";
import {SearchResultPremises} from "../interfaces/searchResultPremises";

@Injectable({
  providedIn: 'root'
})
export class PremiseService {

    private premisesUrl = 'https://localhost:8081/api/dev/premises';

  constructor(
    private messageService: MessageService,
    private http: HttpClient,
  ) {
  }

  getPremises(): Observable<SearchResultPremises> {
    return this.http.get<SearchResultPremises>(this.premisesUrl)
      .pipe(
        tap(_ => this.log('fetched premises')),
        catchError(this.handleError<SearchResultPremises>('getPremises', {premisesGetResponse: []}))
      );
  }

  getPremise(id: number): Observable<SearchResultPremises> {
    const url = `${this.premisesUrl}/${id}`;
    return this.http.get<SearchResultPremises>(url).pipe(
      tap(_ => this.log(`fetched premise id=${id}`)),
      catchError(this.handleError<SearchResultPremises>(`getPremise id=${id}`))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }

  private log(message: string) {
    this.messageService.add(`PremiseService: ${message}`);
  }

}
