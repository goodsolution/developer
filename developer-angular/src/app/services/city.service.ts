import {Injectable} from "@angular/core";
import {catchError, Observable, of, tap} from "rxjs";
import {MessageService} from "./message.service";
import {HttpClient} from "@angular/common/http";
import {SearchResultPremises} from "../interfaces/searchResultPremises";
import {SearchResultCity} from "../interfaces/searchResultCities";

@Injectable({
  providedIn: 'root'
})
export class CityService {

    private cityUrl = 'https://localhost:8081/api/dev/developers/1/investments/cities';

  constructor(
    private messageService: MessageService,
    private http: HttpClient,
  ) {
  }

  getCities(): Observable<SearchResultCity> {
    return this.http.get<SearchResultCity>(this.cityUrl)
      .pipe(
        tap(_ => this.log('fetched premises')),
        catchError(this.handleError<SearchResultCity>('getCities', {cities: []}))
      );
  }

  // getPremise(id: number): Observable<SearchResultPremises> {
  //   const url = `${this.cityUrl}/${id}`;
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
    this.messageService.add(`CityService: ${message}`);
  }

}
