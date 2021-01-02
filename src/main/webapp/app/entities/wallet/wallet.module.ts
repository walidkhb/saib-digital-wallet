import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { WalletComponent } from './wallet.component';
import { WalletDetailComponent } from './wallet-detail.component';
import { WalletUpdateComponent } from './wallet-update.component';
import { WalletDeleteDialogComponent } from './wallet-delete-dialog.component';
import { walletRoute } from './wallet.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(walletRoute)],
  declarations: [WalletComponent, WalletDetailComponent, WalletUpdateComponent, WalletDeleteDialogComponent],
  entryComponents: [WalletDeleteDialogComponent],
})
export class SaibDigitalWalletWalletModule {}
