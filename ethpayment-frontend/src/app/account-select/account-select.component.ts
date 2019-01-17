import {Component, OnDestroy, OnInit} from '@angular/core';
import { AuthenticationService } from '../_services';
import {UploadFileService} from "../upload/list-upload/upload-file.service";
import {AccountdetailsService} from "../_services/accountdetails.service";
import {FileDetailsService} from "../upload/form-upload/file-details.service";
import {EthereumAddressModel} from "../upload/form-upload/ethereumAddress.model";

@Component({
    selector: 'account-select',
    templateUrl: 'account-select.component.html'
})

export class AccountSelectComponent {

    model: any = {};
    loading = false;
    date: Date = null;
    ethereumAddressList: Array<EthereumAddressModel> = [];
    isEthAddressNotEmpty = false;

  accountList: {key: string, value: string}[] = [];

  constructor(
        private authenticationService: AuthenticationService,
        private uploadFileService: UploadFileService,
        private fileDetailsService: FileDetailsService,
        private accountDetailsService: AccountdetailsService) {
    this.setAccountList();
  }

  onSelect() {
    this.accountSelect();
  }

  private setAccountList() {
    this.fileDetailsService.accountList.forEach(accountName => {
      this.accountList.push({
        key: accountName, value: accountName
      })
    });
  }

    accountSelect() {
      this.isEthAddressNotEmpty = false;
      this.accountDetailsService.publishEthereumAddressList([]);
      this.fileDetailsService.publishIsTransferFunds(false);
      this.loading = true;
      this.authenticate();
      console.log('login after body');
      this.accountList.forEach(
        a => {
          if (a.key === this.model.account) {
            this.authenticationService.userName = a.value;
          }
        }
      )
      let listEthereumAddress: any;
      this.accountDetailsService.getAccountAddressList(this.model.account).then(
        value => {
          console.log('getAccountAddressList ', this.model.account);
          listEthereumAddress = value;
          this.ethereumAddressList = listEthereumAddress.body;
          this.ethereumAddressList.forEach((address: EthereumAddressModel) => {
            if (address.accountName === this.model.account) {
              this.accountDetailsService.publishEthereumAddressList(this.ethereumAddressList);
              this.isEthAddressNotEmpty = true;
            }
            }
          )
        }
      )
      this.uploadFileService.getFileList().then(
        value => {
          let fileList: any;
          fileList = value.length;
          this.uploadFileService.publishIsFileListEmpty(fileList > 0);
        }
      )

      let accountDate: any;
      this.accountDetailsService.getAccountDate().then(
        val => {
          accountDate = val;
          this.date = accountDate;
          this.accountDetailsService.date = this.date;
          this.accountDetailsService.publishScheduledDate(this.date);
        }, error => {
          accountDate = null;
        }
      )
      this.loading = false;
      this.authenticationService.publishIsSelectUser(true);
    }

  private authenticate() {
    this.authenticationService.publishIsSelectUser(false);
    this.authenticationService.login(this.model.account)
      .then(
        data => {

        },
        error => {
        });
  }


}
