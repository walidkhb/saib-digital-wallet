import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { KycTransactionsComponent } from './kyc-transactions.component';
import { KycTransactionsDetailComponent } from './kyc-transactions-detail.component';
import { KycTransactionsUpdateComponent } from './kyc-transactions-update.component';
import { KycTransactionsDeleteDialogComponent } from './kyc-transactions-delete-dialog.component';
import { kycTransactionsRoute } from './kyc-transactions.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(kycTransactionsRoute)],
  declarations: [
    KycTransactionsComponent,
    KycTransactionsDetailComponent,
    KycTransactionsUpdateComponent,
    KycTransactionsDeleteDialogComponent,
  ],
  entryComponents: [KycTransactionsDeleteDialogComponent],
})
export class SaibDigitalWalletKycTransactionsModule {}
