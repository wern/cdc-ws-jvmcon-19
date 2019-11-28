import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Greeting, Greetings} from './Greetings';
import {map} from 'rxjs/operators';

@Injectable()
export class GreetingService {

  private BASE_URL = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) {
  }

  loadGreetings(): Observable<Greetings> {
    return this.httpClient.get<Greetings>(this.BASE_URL + '/greetings');
  }

  loadGreetingWithType(type: string): Observable<Greeting> {
    return this.httpClient.get<any>(this.BASE_URL + '/greetings/' + type)
      .pipe(map(response => response.greeting));
  }

  deleteGreeting(greeting: Greeting): Observable<number> {
    return this.httpClient.delete<object>(this.BASE_URL + '/greetings/' + greeting.type, {observe: 'response'})
      .pipe(map(response => response.status));
  }

  saveGreeting(greeting: Greeting): Observable<number> {
    return this.httpClient.put<object>(
      this.BASE_URL + '/greetings/' + greeting.type, {greeting},
      { headers: new HttpHeaders({
          'Content-Type':  'application/json'
        }), observe: 'response'})
      .pipe(map(response => response.status));
  }
}
