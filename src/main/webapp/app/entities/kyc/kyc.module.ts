import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { KycComponent } from './kyc.component';
import { KycDetailComponent } from './kyc-detail.component';
import { KycUpdateComponent } from './kyc-update.component';
import { KycDeleteDialogComponent } from './kyc-delete-dialog.component';
import { kycRoute } from './kyc.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(kycRoute)],
  declarations: [KycComponent, KycDetailComponent, KycUpdateComponent, KycDeleteDialogComponent],
  entryComponents: [KycDeleteDialogComponent],
})
export class SaibDigitalWalletKycModule {}
