import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpResponse, HttpEventType } from '@angular/common/http';
import { UploadFileService } from '../list-upload/upload-file.service';
import {FileDetailsService} from "./file-details.service";
import {Subject} from "rxjs/Subject";
import {parseHttpResponse} from "selenium-webdriver/http";
import {EthereumAddressModel} from "./ethereumAddress.model";
import {AccountdetailsService} from "../../_services/accountdetails.service";

@Component({
  selector: 'form-upload',
  templateUrl: './form-upload.component.html',
  styleUrls: ['./form-upload.component.css']
})
export class FormUploadComponent implements OnInit {

  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 }
  showVar: boolean= false;
  formLoading: boolean = false;
  date: Date = null;
  isFileListEmpty = false;
  isUploadFile = false;

  constructor(private uploadService: UploadFileService,
              private fileDetailsService: FileDetailsService) {
  }

  ngOnInit() {
    this.uploadService.getFileList().then(
      value => {
        let fileList: any;
        fileList = value.length;
        this.isFileListEmpty = fileList > 0;
      }
    )
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }

  upload() {
    this.isUploadFile = true;
    this.formLoading = true;
    this.progress.percentage = 0;
    this.currentFileUpload = this.selectedFiles.item(0)
    this.uploadService.pushFileToStorage(this.currentFileUpload).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        this.progress.percentage = Math.round(100 * event.loaded / event.total);
      } else if (event instanceof HttpResponse && this.progress.percentage == 100) {
        console.log('File is completely uploaded! ' +event.body.valueOf());
        this.showVar = true;
        this.formLoading = false;
        let accountList: any = event.body.valueOf();
        let responseList: string[] = accountList;
        this.fileDetailsService.setAccountList(responseList);
      }
    })

    this.selectedFiles = undefined
  }
}
