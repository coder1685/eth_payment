import {Component, OnInit} from '@angular/core';
import {FileDetailsService} from "../upload/form-upload/file-details.service";
import {User} from "../_models";
import {AccountdetailsService} from "../_services/accountdetails.service";
import {AuthenticationService} from "../_services";
import {UploadFileService} from "../upload/list-upload/upload-file.service";
import {Router} from "@angular/router";

@Component({
  selector: 'ethpayment',
  templateUrl: './ethpayment.component.html',
  styleUrls: [ './ethpayment.component.css' ]
})
export class EthpaymentComponent implements OnInit{
  title = 'Ethereum Payment System';
  isTransferFunds = false;
  currentUser: User;
  date: Date = null;
  isFileListEmpty = false;
  loggedInUser: string = null;
  isSelectUser = false;
  isEthAddress= false;

  constructor(private fileDetailsService: FileDetailsService,
              private accountDetailsService: AccountdetailsService,
              private authenticationService: AuthenticationService,
              private uploadFileService: UploadFileService,
              private router: Router) {
    this.loggedInUser = this.authenticationService.userName;
    console.log('ethpayment constructor isSelectUser ', this.isSelectUser);
  }

  ngOnInit(): void {
    this.fileDetailsService.isTransferFundsSelected$.subscribe(value => {
      this.isTransferFunds = value;
    });

    this.authenticationService.isSelectUserSelected$.subscribe(
      value => {
        this.isSelectUser = value;
      });

     this.uploadFileService.isFileListEmptySelected$.subscribe(
      value => {
        this.isFileListEmpty = value;
      }
    )

    this.accountDetailsService.ethereumAddressListSelected$.subscribe(
      value => {
        this.accountDetailsService.setEthereumAddressList(value);
        this.isEthAddress = true;
      }
    )
    console.log('ethpayment isSelectUser ', this.isSelectUser);
  }

  onClick() {
    localStorage.removeItem('currentUser');
    this.router.navigate(['login']);
  }
}

