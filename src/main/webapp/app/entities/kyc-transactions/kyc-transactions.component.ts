import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IKycTransactions } from 'app/shared/model/kyc-transactions.model';
import { KycTransactionsService } from './kyc-transactions.service';
import { KycTransactionsDeleteDialogComponent } from './kyc-transactions-delete-dialog.component';

@Component({
  selector: 'jhi-kyc-transactions',
  templateUrl: './kyc-transactions.component.html',
})
export class KycTransactionsComponent implements OnInit, OnDestroy {
  kycTransactions?: IKycTransactions[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected kycTransactionsService: KycTransactionsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.kycTransactionsService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IKycTransactions[]>) => (this.kycTransactions = res.body || []));
      return;
    }

    this.kycTransactionsService.query().subscribe((res: HttpResponse<IKycTransactions[]>) => (this.kycTransactions = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInKycTransactions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IKycTransactions): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInKycTransactions(): void {
    this.eventSubscriber = this.eventManager.subscribe('kycTransactionsListModification', () => this.loadAll());
  }

  delete(kycTransactions: IKycTransactions): void {
    const modalRef = this.modalService.open(KycTransactionsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.kycTransactions = kycTransactions;
  }
}
