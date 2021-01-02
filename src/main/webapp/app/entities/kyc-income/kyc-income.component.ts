import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IKycIncome } from 'app/shared/model/kyc-income.model';
import { KycIncomeService } from './kyc-income.service';
import { KycIncomeDeleteDialogComponent } from './kyc-income-delete-dialog.component';

@Component({
  selector: 'jhi-kyc-income',
  templateUrl: './kyc-income.component.html',
})
export class KycIncomeComponent implements OnInit, OnDestroy {
  kycIncomes?: IKycIncome[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected kycIncomeService: KycIncomeService,
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
      this.kycIncomeService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IKycIncome[]>) => (this.kycIncomes = res.body || []));
      return;
    }

    this.kycIncomeService.query().subscribe((res: HttpResponse<IKycIncome[]>) => (this.kycIncomes = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInKycIncomes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IKycIncome): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInKycIncomes(): void {
    this.eventSubscriber = this.eventManager.subscribe('kycIncomeListModification', () => this.loadAll());
  }

  delete(kycIncome: IKycIncome): void {
    const modalRef = this.modalService.open(KycIncomeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.kycIncome = kycIncome;
  }
}
