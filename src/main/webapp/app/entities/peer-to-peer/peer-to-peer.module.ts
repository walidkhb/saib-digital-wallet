import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaibDigitalWalletSharedModule } from 'app/shared/shared.module';
import { PeerToPeerComponent } from './peer-to-peer.component';
import { PeerToPeerDetailComponent } from './peer-to-peer-detail.component';
import { PeerToPeerUpdateComponent } from './peer-to-peer-update.component';
import { PeerToPeerDeleteDialogComponent } from './peer-to-peer-delete-dialog.component';
import { peerToPeerRoute } from './peer-to-peer.route';

@NgModule({
  imports: [SaibDigitalWalletSharedModule, RouterModule.forChild(peerToPeerRoute)],
  declarations: [PeerToPeerComponent, PeerToPeerDetailComponent, PeerToPeerUpdateComponent, PeerToPeerDeleteDialogComponent],
  entryComponents: [PeerToPeerDeleteDialogComponent],
})
export class SaibDigitalWalletPeerToPeerModule {}
