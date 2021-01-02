import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPeerToPeer } from 'app/shared/model/peer-to-peer.model';
import { PeerToPeerService } from './peer-to-peer.service';

@Component({
  templateUrl: './peer-to-peer-delete-dialog.component.html',
})
export class PeerToPeerDeleteDialogComponent {
  peerToPeer?: IPeerToPeer;

  constructor(
    protected peerToPeerService: PeerToPeerService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.peerToPeerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('peerToPeerListModification');
      this.activeModal.close();
    });
  }
}
