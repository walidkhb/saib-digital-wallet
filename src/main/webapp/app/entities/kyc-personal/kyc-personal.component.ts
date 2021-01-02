import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IKycPersonal } from 'app/shared/model/kyc-personal.model';
import { KycPersonalService } from './kyc-personal.service';
import { KycPersonalDeleteDialogComponent } from './kyc-personal-delete-dialog.component';

@Component({
  selector: 'jhi-kyc-personal',
  templateUrl: './kyc-personal.component.html',
})
export class KycPersonalComponent implements OnInit, OnDestroy {
  kycPersonals?: IKycPersonal[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected kycPersonalService: KycPersonalService,
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
      this.kycPersonalService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IKycPersonal[]>) => (this.kycPersonals = res.body || []));
      return;
    }

    this.kycPersonalService.query().subscribe((res: HttpResponse<IKycPersonal[]>) => (this.kycPersonals = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInKycPersonals();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IKycPersonal): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInKycPersonals(): void {
    this.eventSubscriber = this.eventManager.subscribe('kycPersonalListModification', () => this.loadAll());
  }

  delete(kycPersonal: IKycPersonal): void {
    const modalRef = this.modalService.open(KycPersonalDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.kycPersonal = kycPersonal;
  }
}
