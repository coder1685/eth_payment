import {AppComponent} from './app.component';
import {DetailsUploadComponent} from './upload/details-upload/details-upload.component';
import {FormUploadComponent} from './upload/form-upload/form-upload.component';
import {ListUploadComponent} from './upload/list-upload/list-upload.component';
import {UploadFileService} from './upload/list-upload/upload-file.service';
import {AddressListComponent} from "./upload/address-list/adress-list.component";
import {FileDetailsService} from "./upload/form-upload/file-details.service";
import {LoadingModule} from "ngx-loading";
import {CalendarModule, DataTableModule, InputTextModule} from "primeng/primeng";
import {ButtonModule} from "primeng/button";
import {DialogModule} from "primeng/dialog";
import {EthpaymentComponent} from "./ethpayment/ethpayment.component";
import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {AgGridModule} from "ag-grid-angular";
import {AccountSelectComponent} from "./account-select";
import {AuthenticationService} from "./_services";
import {AuthGuard} from "./_guards";
import {ConfirmationPopoverModule} from "angular-confirmation-popover";
import {AccountdetailsService} from "./_services/accountdetails.service";
import {HeaderComponent} from "./header/header.component";
import {FooterComponent} from "./footer/footer.component";
import {LoginComponent} from "./login";
import {routing} from "./app.routing";
import {HomeComponent} from "./home/home.component";
import {PageNotFoundComponent} from "./pagenotfound/pagenotfound.component";
import {SelectivePreloadingStrategy} from "./pagenotfound/selective-preloading-strategy";
import {WelcomeComponent} from "./welcome/welcome.component";
import {EthpaymentFooterComponent} from "./ethpayment/footer/ethpayment.footer.component";
import {EthpaymentHeaderComponent} from "./ethpayment/header/ethpayment.header.component";

@NgModule({
  declarations: [
    AppComponent,
    DetailsUploadComponent,
    FormUploadComponent,
    ListUploadComponent,
    AddressListComponent,
    EthpaymentComponent,
    AccountSelectComponent,
    HeaderComponent,
    FooterComponent,
    LoginComponent,
    HomeComponent,
    WelcomeComponent,
    PageNotFoundComponent,
    EthpaymentFooterComponent,
    EthpaymentHeaderComponent
  ],
  imports: [
    FormsModule,
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    LoadingModule,
    BrowserAnimationsModule,
    InputTextModule,
    CalendarModule,
    DataTableModule,
    ButtonModule,
    DialogModule,
    AgGridModule.withComponents([AddressListComponent]),
    ConfirmationPopoverModule.forRoot({
      confirmButtonType: 'danger'
    }),
    routing,
  ],
  providers: [UploadFileService, FileDetailsService, SelectivePreloadingStrategy, AuthGuard, AuthenticationService, AccountdetailsService],
  bootstrap: [AppComponent]
})
export class AppModule {

}
