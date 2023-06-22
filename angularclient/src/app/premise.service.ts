import {Injectable} from "@angular/core";
import {Premise} from "./premise";
import {Observable, of} from "rxjs";
import {MessageService} from "./message.service";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, map, tap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PremiseService {
  private readonly premisesUrl: string;

  constructor(
    private messageService: MessageService,
    private http: HttpClient,
  ) {
    this.premisesUrl = 'https://localhost:8081/api/dev/premises';
  }

  private log(message: string) {
    this.messageService.add(`PremiseService: ${message}`);
  }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  getPremises(): Observable<Premise[]> {
    return this.http.get<Premise[]>(this.premisesUrl)
      .pipe(
        tap(_ => this.log('fetched premises')),
        catchError(this.handleError<Premise[]>('getPremises', []))
      );
  }

  getPremise(id: number): Observable<Premise> {
    const url = `${this.premisesUrl}/${id}`;
    return this.http.get<Premise>(url)
      .pipe(
        tap(_ => this.log(`fetched premise id=${id}`)),
        catchError(this.handleError<Premise>(`getPremise id=${id}`))
      );
  }

  updatePremise(premise: Premise): Observable<any> {
    return this.http.put(this.premisesUrl, premise, this.httpOptions).pipe(
      tap(_ => this.log(`updated premise id=${premise.id}`)),
      catchError(this.handleError<any>('updatePremise'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }

}
