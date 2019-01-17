import {AfterViewInit, Component, OnInit} from "@angular/core";
import {FileDetailsService} from "../form-upload/file-details.service";
import {ColDef, GridOptions} from "ag-grid";
import {EthereumAddressModel} from "../form-upload/ethereumAddress.model";
import {AccountdetailsService} from "../../_services/accountdetails.service";
import * as moment from "moment";

@Component({
  selector: 'address-list',
  templateUrl: './adress-list.component.html',
})
export class AddressListComponent implements OnInit, AfterViewInit {

  model: any = {};
  gridOptions: GridOptions;
  columnDefs: any[];
  rowData: EthereumAddressModel[] = [];
  isTransferFunds: boolean = false;

  static readonly headerHeight: number = 25;
  static readonly rowHeight: number = 25;

  public gridHeight: string;

  public loading: boolean = false;

  ethereumAddressList: Array<EthereumAddressModel> = [];

  date: Date = null;
  isDateNull = true;

  popoverTitle: string = 'Are you sure?';
  popoverMessage: string = 'Do you really <b>want</b> to reschedule fund transfer?';
  confirmText: string = 'Yes <i class="glyphicon glyphicon-ok"></i>';
  cancelText: string = 'No <i class="glyphicon glyphicon-remove"></i>';

  constructor(private fileDetailsService: FileDetailsService,
              private accountDetailsService: AccountdetailsService) {
    this.gridOptions = <GridOptions>{};
    this.ethereumAddressList = this.accountDetailsService.listethereumAddress;
    this.isDateNull = true;
    this.accountDetailsService.scheduledDateSelected$.subscribe(value => {
      if (value != null) {
        this.model.date = moment(this.accountDetailsService.date).toDate();
        this.date = this.model.date;
        this.isDateNull = false;
      }
    })
  }

  ngOnInit(): void {
    this.initializeGrid();
    this.populateGridData();
  }

  ngAfterViewInit() {
  }

  initializeGrid() {
    this.gridOptions = {
      rowData: this.rowData,
      headerHeight: AddressListComponent.headerHeight,
      rowHeight: AddressListComponent.rowHeight,
      columnDefs: AddressListComponent.buildColumnDefinitions()
    };
    this.gridHeight = this.ethereumAddressList.length == 1
      ? (((this.ethereumAddressList.length * (AddressListComponent.rowHeight + 35))
      + AddressListComponent.headerHeight).toString() + 'px') :
      (((this.ethereumAddressList.length * (AddressListComponent.rowHeight + 17))
                          + AddressListComponent.headerHeight).toString() + 'px');
  }

  public static buildColumnDefinitions(): ColDef[] {
    return [
      {headerName: 'Name', minWidth: 40, maxWidth: 60, field: 'name'},
      {headerName: 'Ethereum Address', minWidth: 350, maxWidth: 550, field: 'ethereumAddress'},
      {headerName: 'Amount', minWidth: 10, maxWidth: 80, field: 'amount'}
    ]
  }

  populateGridData() {
    let total: number = 0;
    console.log('populateGridData this.ethereumAddressList', this.ethereumAddressList.length);
    this.ethereumAddressList.forEach((line: EthereumAddressModel) => {
      total = Number.parseFloat((line.amount + total).toFixed(3));
      this.rowData.push(line);
    });
    this.rowData.push(new EthereumAddressModel( '','Total', '' , total))
  }

  onGridReady(params) {
    params.api.sizeColumnsToFit();
  }

  selectAllRows() {
    this.gridOptions.api.selectAll();
  }

  cancelClicked(): boolean {
    return false;
  }

  transferFunds(isReschedule: string) {
    this.loading = true;
    this.date = this.model.date;
    this.fileDetailsService.publishIsTransferFunds(false);
    this.fileDetailsService.transferFunds(this.date.toISOString(), isReschedule).then(
      event => {
        this.isTransferFunds = true;
        this.loading = false;
        this.fileDetailsService.publishIsTransferFunds(true);
      }, error => {
        this.isTransferFunds = true;
        this.loading = false;
        this.fileDetailsService.publishIsTransferFunds(true);
      }
    )
  }
}

