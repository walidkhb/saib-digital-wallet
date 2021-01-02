import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IKyc } from 'app/shared/model/kyc.model';
import { KycService } from './kyc.service';
import { KycDeleteDialogComponent } from './kyc-delete-dialog.component';

@Component({
  selector: 'jhi-kyc',
  templateUrl: './kyc.component.html',
})
export class KycComponent implements OnInit, OnDestroy {
  kycs?: IKyc[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected kycService: KycService,
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
      this.kycService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IKyc[]>) => (this.kycs = res.body || []));
      return;
    }

    this.kycService.query().subscribe((res: HttpResponse<IKyc[]>) => (this.kycs = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInKycs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IKyc): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInKycs(): void {
    this.eventSubscriber = this.eventManager.subscribe('kycListModification', () => this.loadAll());
  }

  delete(kyc: IKyc): void {
    const modalRef = this.modalService.open(KycDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.kyc = kyc;
  }
}
