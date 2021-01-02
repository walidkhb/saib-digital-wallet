import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { TopUpComponent } from './top-up.component';
import { TopUpDetailComponent } from './top-up-detail.component';
import { TopUpUpdateComponent } from './top-up-update.component';
import { TopUpDeleteDialogComponent } from './top-up-delete-dialog.component';
import { topUpRoute } from './top-up.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(topUpRoute)],
  declarations: [TopUpComponent, TopUpDetailComponent, TopUpUpdateComponent, TopUpDeleteDialogComponent],
  entryComponents: [TopUpDeleteDialogComponent],
})
export class SaibDigitalWalletTopUpModule {}
