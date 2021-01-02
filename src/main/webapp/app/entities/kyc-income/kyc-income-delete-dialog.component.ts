import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IKycIncome } from 'app/shared/model/kyc-income.model';
import { KycIncomeService } from './kyc-income.service';

@Component({
  templateUrl: './kyc-income-delete-dialog.component.html',
})
export class KycIncomeDeleteDialogComponent {
  kycIncome?: IKycIncome;

  constructor(protected kycIncomeService: KycIncomeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kycIncomeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('kycIncomeListModification');
      this.activeModal.close();
    });
  }
}
