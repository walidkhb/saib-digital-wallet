import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IKycPersonal } from 'app/shared/model/kyc-personal.model';
import { KycPersonalService } from './kyc-personal.service';

@Component({
  templateUrl: './kyc-personal-delete-dialog.component.html',
})
export class KycPersonalDeleteDialogComponent {
  kycPersonal?: IKycPersonal;

  constructor(
    protected kycPersonalService: KycPersonalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kycPersonalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('kycPersonalListModification');
      this.activeModal.close();
    });
  }
}
