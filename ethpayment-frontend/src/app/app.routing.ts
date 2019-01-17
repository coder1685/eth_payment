import { Routes, RouterModule } from '@angular/router';

import {AuthGuard} from './_guards';
import {EthpaymentComponent} from './ethpayment/ethpayment.component';
import {LoginComponent} from './login';
import {WelcomeComponent} from './welcome/welcome.component';

const appRoutes: Routes = [
    { path: 'welcome', component: WelcomeComponent },
    { path: 'ethpayment', component: EthpaymentComponent, canActivate: [AuthGuard] },
    { path: 'login', component: LoginComponent },
    { path: '', redirectTo: '/welcome', pathMatch: 'full' },

    // otherwise redirect to home
    { path: '**', component: WelcomeComponent }
];

export const routing = RouterModule.forRoot(appRoutes);
