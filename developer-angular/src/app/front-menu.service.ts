import {Injectable} from '@angular/core';
import {MessageService} from "./message.service";
import {HttpClient} from "@angular/common/http";
import {catchError, Observable, of, tap} from "rxjs";
import {SearchResultFrontMenu} from "./searchResultFrontMenu";

@Injectable({
  providedIn: 'root'
})
export class FrontMenuService {

  private frontMenuUrl = 'https://localhost:8081/api/dev/developers/1/front-menu'

  constructor(
    private messageService: MessageService,
    private http: HttpClient,
  ) { }

  getMenu(): Observable<SearchResultFrontMenu> {
    return this.http.get<SearchResultFrontMenu>(this.frontMenuUrl)
      .pipe(
        tap(_ => this.log('fetched premises')),
        catchError(this.handleError<SearchResultFrontMenu>('getMenu', {menus: []}))
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
    this.messageService.add(`Front Menu Service: ${message}`);
  }


}
