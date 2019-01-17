import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {UploadFileService} from './upload-file.service';
import * as moment from "moment";
import {AccountdetailsService} from "../../_services/accountdetails.service";
import {FileDetailsService} from "../form-upload/file-details.service";

@Component({
  selector: 'list-upload',
  templateUrl: './list-upload.component.html'
})
export class ListUploadComponent implements OnInit {

  showFile = false;
  fileUploads: Observable<string[]>;
  public isListLoad: boolean = false;
  fileList: string[] = [];
  date: Date = null;
  isTransferFunds:boolean = false;

  constructor(private uploadService: UploadFileService,
              private fileDetailsService: FileDetailsService,
              private accountDetailsService: AccountdetailsService) {
    if(this.accountDetailsService.date != null){
      this.date = moment(this.accountDetailsService.date).toDate();
    }
  }

  ngOnInit() {
    this.fileDetailsService.isTransferFundsSelected$.subscribe(value => {
      this.isTransferFunds = value;
    })
  }

  showFiles(enable: boolean) {
    this.showFile = enable;
    if (enable) {
      this.isListLoad = true;
      let accountFileUpload: any;
      accountFileUpload = this.uploadService.getFiles();
      this.fileUploads = accountFileUpload;
      accountFileUpload.subscribe(files =>{
        let file:any;
        file = files;
        this.fileList = file;
        this.isListLoad = false;
      }, error=>{
        this.isListLoad = false;
        console.log("error");
      })
    }
  }
}
