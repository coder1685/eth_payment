import {Injectable} from '@angular/core';
import {HttpClient, HttpRequest, HttpEvent} from '@angular/common/http';
import {Observable} from "rxjs/Observable";
import {Subject} from "rxjs/Subject";

@Injectable()
export class UploadFileService {

  private isFileListEmpty = new Subject<boolean>();
  public isFileListEmptySelected$ = this.isFileListEmpty.asObservable();

  constructor(private http: HttpClient) {}

  pushFileToStorage(file: File): Observable<HttpEvent<{}>> {
    let formdata: FormData = new FormData();

    formdata.append('file', file);
    const req = new HttpRequest('POST',
      '/api/ethPayment/accountList', formdata, {
        reportProgress: true,
        responseType: 'json'
      });
    return this.http.request(req);
  }

  getFiles(): Observable<Object> {
    return this.http.get('/api/ethPayment/getallfiles');
  }

  getFileList(): Promise<any> {
    return this.http.get('/api/ethPayment/getFileList').toPromise();
  }

  publishIsFileListEmpty(isFileListEmpty: boolean) {
    this.isFileListEmpty.next(isFileListEmpty);
  }
}
