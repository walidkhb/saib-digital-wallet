import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IKycTransactions } from 'app/shared/model/kyc-transactions.model';
import { KycTransactionsService } from './kyc-transactions.service';

@Component({
  templateUrl: './kyc-transactions-delete-dialog.component.html',
})
export class KycTransactionsDeleteDialogComponent {
  kycTransactions?: IKycTransactions;

  constructor(
    protected kycTransactionsService: KycTransactionsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kycTransactionsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('kycTransactionsListModification');
      this.activeModal.close();
    });
  }
}
