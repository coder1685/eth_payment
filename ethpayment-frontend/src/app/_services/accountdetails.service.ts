import {HttpClient, HttpParams, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Injectable} from "@angular/core";
import {EthereumAddressModel} from "../upload/form-upload/ethereumAddress.model";
import {Subject} from "rxjs/Subject";

@Injectable()
export class AccountdetailsService {

  date: Date = null;

  private scheduledDate = new Subject<Date>();
  public scheduledDateSelected$ = this.scheduledDate.asObservable();

  public listethereumAddress: Array<EthereumAddressModel> = [];

  private ethereumAddressList = new Subject<Array<EthereumAddressModel>>();
  public ethereumAddressListSelected$ = this.ethereumAddressList.asObservable();

  constructor(private http: HttpClient) { }

  getAccountDate(): Promise<any> {
    return this.http.get('/api/ethPayment/getAccountDetails').toPromise();
  }

  getAccountAddressList(accountName: string): Promise<any> {
    const req = new HttpRequest('POST',
      '/api/ethPayment/addressList', null, {
        reportProgress: true,
        responseType: 'json',
        params: new HttpParams()
          .set('accountName', accountName)
      });
    return this.http.request(req).toPromise();
  }

  publishEthereumAddressList(ethereumAddressList: Array<EthereumAddressModel>) {
    this.ethereumAddressList.next(ethereumAddressList);
  }

  publishScheduledDate(date: Date) {
    this.scheduledDate.next(date);
  }

  public setEthereumAddressList(ethereumList: Array<EthereumAddressModel>) {
    this.listethereumAddress = ethereumList;
  }
}
