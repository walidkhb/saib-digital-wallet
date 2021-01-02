import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { KycPersonalComponent } from './kyc-personal.component';
import { KycPersonalDetailComponent } from './kyc-personal-detail.component';
import { KycPersonalUpdateComponent } from './kyc-personal-update.component';
import { KycPersonalDeleteDialogComponent } from './kyc-personal-delete-dialog.component';
import { kycPersonalRoute } from './kyc-personal.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(kycPersonalRoute)],
  declarations: [KycPersonalComponent, KycPersonalDetailComponent, KycPersonalUpdateComponent, KycPersonalDeleteDialogComponent],
  entryComponents: [KycPersonalDeleteDialogComponent],
})
export class SaibDigitalWalletKycPersonalModule {}
