import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IKyc } from 'app/shared/model/kyc.model';
import { KycService } from './kyc.service';

@Component({
  templateUrl: './kyc-delete-dialog.component.html',
})
export class KycDeleteDialogComponent {
  kyc?: IKyc;

  constructor(protected kycService: KycService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kycService.delete(id).subscribe(() => {
      this.eventManager.broadcast('kycListModification');
      this.activeModal.close();
    });
  }
}
