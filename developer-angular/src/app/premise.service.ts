import {Injectable} from "@angular/core";
import {Premise} from "./premise";
import {catchError, Observable, of, tap} from "rxjs";
import {MessageService} from "./message.service";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class PremiseService {
  private premisesUrl = 'https://localhost:8081/api/dev/premises';  // URL to web api

  constructor(
    private messageService: MessageService,
    private http: HttpClient,
  ) {
  }

  getPremises(): Observable<Premise[]> {
    return this.http.get<Premise[]>(this.premisesUrl)
      .pipe(
        tap(_ => this.log('fetched premises')),
        catchError(this.handleError<Premise[]>('getPremises', []))
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }

  getPremise(id: number): Observable<Premise> {
    const url = `${this.premisesUrl}/${id}`;
    return this.http.get<Premise>(url).pipe(
      tap(_ => this.log(`fetched premise id=${id}`)),
      catchError(this.handleError<Premise>(`getPremise id=${id}`))
    );
  }

  private log(message: string) {
    this.messageService.add(`PremiseService: ${message}`);
  }

}
