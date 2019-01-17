import {Injectable} from "@angular/core";
import {HttpClient, HttpEvent, HttpParams, HttpRequest} from "@angular/common/http";
import {Subject} from "rxjs/Subject";
import {EthereumAddressModel} from "./ethereumAddress.model";
import {Observable} from "rxjs/Observable";

@Injectable()
export class FileDetailsService {

  public accountList: Array<string> = []

  private isTransferFunds = new Subject<boolean>();
  public isTransferFundsSelected$ = this.isTransferFunds.asObservable();

  constructor(private http: HttpClient) {}

  setAccountList(accountList: Array<string>) {
    this.accountList = accountList;
  }

  transferFunds(date: string, isReschedule: string): Promise<any> {

    const req = new HttpRequest('POST',
      '/api/ethPayment/transferFundsAndGenerateReport', null, {
        reportProgress: true,
        responseType: 'json',
        params: new HttpParams()
          .set('date', date)
          .set('isReschedule', isReschedule)
      });
    return this.http.request(req).toPromise();
  }

  publishIsTransferFunds(isTransferFunds: boolean){
    this.isTransferFunds.next(isTransferFunds);
  }

}
