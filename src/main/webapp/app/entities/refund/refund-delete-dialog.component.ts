import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRefund } from 'app/shared/model/refund.model';
import { RefundService } from './refund.service';

@Component({
  templateUrl: './refund-delete-dialog.component.html',
})
export class RefundDeleteDialogComponent {
  refund?: IRefund;

  constructor(protected refundService: RefundService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.refundService.delete(id).subscribe(() => {
      this.eventManager.broadcast('refundListModification');
      this.activeModal.close();
    });
  }
}
