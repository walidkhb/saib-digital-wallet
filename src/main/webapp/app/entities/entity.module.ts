import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.SaibDigitalWalletCustomerModule),
      },
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.SaibDigitalWalletAddressModule),
      },
      {
        path: 'kyc',
        loadChildren: () => import('./kyc/kyc.module').then(m => m.SaibDigitalWalletKycModule),
      },
      {
        path: 'kyc-income',
        loadChildren: () => import('./kyc-income/kyc-income.module').then(m => m.SaibDigitalWalletKycIncomeModule),
      },
      {
        path: 'kyc-transactions',
        loadChildren: () => import('./kyc-transactions/kyc-transactions.module').then(m => m.SaibDigitalWalletKycTransactionsModule),
      },
      {
        path: 'kyc-personal',
        loadChildren: () => import('./kyc-personal/kyc-personal.module').then(m => m.SaibDigitalWalletKycPersonalModule),
      },
      {
        path: 'peer-to-peer',
        loadChildren: () => import('./peer-to-peer/peer-to-peer.module').then(m => m.SaibDigitalWalletPeerToPeerModule),
      },
      {
        path: 'top-up',
        loadChildren: () => import('./top-up/top-up.module').then(m => m.SaibDigitalWalletTopUpModule),
      },
      {
        path: 'refund',
        loadChildren: () => import('./refund/refund.module').then(m => m.SaibDigitalWalletRefundModule),
      },
      {
        path: 'wallet',
        loadChildren: () => import('./wallet/wallet.module').then(m => m.SaibDigitalWalletWalletModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SaibDigitalWalletEntityModule {}
