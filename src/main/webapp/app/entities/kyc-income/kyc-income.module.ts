import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { KycIncomeComponent } from './kyc-income.component';
import { KycIncomeDetailComponent } from './kyc-income-detail.component';
import { KycIncomeUpdateComponent } from './kyc-income-update.component';
import { KycIncomeDeleteDialogComponent } from './kyc-income-delete-dialog.component';
import { kycIncomeRoute } from './kyc-income.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(kycIncomeRoute)],
  declarations: [KycIncomeComponent, KycIncomeDetailComponent, KycIncomeUpdateComponent, KycIncomeDeleteDialogComponent],
  entryComponents: [KycIncomeDeleteDialogComponent],
})
export class SaibDigitalWalletKycIncomeModule {}
