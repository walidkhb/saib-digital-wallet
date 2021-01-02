import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITopUp } from 'app/shared/model/top-up.model';
import { TopUpService } from './top-up.service';

@Component({
  templateUrl: './top-up-delete-dialog.component.html',
})
export class TopUpDeleteDialogComponent {
  topUp?: ITopUp;

  constructor(protected topUpService: TopUpService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.topUpService.delete(id).subscribe(() => {
      this.eventManager.broadcast('topUpListModification');
      this.activeModal.close();
    });
  }
}
