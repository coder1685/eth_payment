import { Injectable } from '@angular/core';
import {HttpClient, HttpParams, HttpRequest} from '@angular/common/http';
import {Subject} from "rxjs/Subject";
import {Router} from "@angular/router";

@Injectable()
export class AuthenticationService {

  public userName: string;
  private isSelectUser = new Subject<boolean>();
  public isSelectUserSelected$ = this.isSelectUser.asObservable();

  constructor(private http: HttpClient,
              private router: Router) { }

  login(accountName: string): Promise<any> {

    const req = new HttpRequest('POST',
      '/api/ethPayment/login', null, {
        reportProgress: true,
        responseType: 'json',
        params: new HttpParams()
          .set('accountName', accountName)
      });
    return this.http.request(req).toPromise();
  }

  authenticate(user: string) {
    localStorage.setItem('currentUser', JSON.stringify(user));
  }

  publishIsSelectUser(isSelectUser: boolean) {
    this.isSelectUser.next(isSelectUser);
  }

  logout() {
      localStorage.removeItem('currentUser');
  }
}
