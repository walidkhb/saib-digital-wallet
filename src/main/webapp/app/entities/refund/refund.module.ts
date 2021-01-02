import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { RefundComponent } from './refund.component';
import { RefundDetailComponent } from './refund-detail.component';
import { RefundUpdateComponent } from './refund-update.component';
import { RefundDeleteDialogComponent } from './refund-delete-dialog.component';
import { refundRoute } from './refund.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(refundRoute)],
  declarations: [RefundComponent, RefundDetailComponent, RefundUpdateComponent, RefundDeleteDialogComponent],
  entryComponents: [RefundDeleteDialogComponent],
})
export class SaibDigitalWalletRefundModule {}
