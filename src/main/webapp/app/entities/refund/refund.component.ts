import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRefund } from 'app/shared/model/refund.model';
import { RefundService } from './refund.service';
import { RefundDeleteDialogComponent } from './refund-delete-dialog.component';

@Component({
  selector: 'jhi-refund',
  templateUrl: './refund.component.html',
})
export class RefundComponent implements OnInit, OnDestroy {
  refunds?: IRefund[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected refundService: RefundService,
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
      this.refundService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IRefund[]>) => (this.refunds = res.body || []));
      return;
    }

    this.refundService.query().subscribe((res: HttpResponse<IRefund[]>) => (this.refunds = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRefunds();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRefund): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRefunds(): void {
    this.eventSubscriber = this.eventManager.subscribe('refundListModification', () => this.loadAll());
  }

  delete(refund: IRefund): void {
    const modalRef = this.modalService.open(RefundDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.refund = refund;
  }
}
