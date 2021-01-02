import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITopUp } from 'app/shared/model/top-up.model';
import { TopUpService } from './top-up.service';
import { TopUpDeleteDialogComponent } from './top-up-delete-dialog.component';

@Component({
  selector: 'jhi-top-up',
  templateUrl: './top-up.component.html',
})
export class TopUpComponent implements OnInit, OnDestroy {
  topUps?: ITopUp[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected topUpService: TopUpService,
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
      this.topUpService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ITopUp[]>) => (this.topUps = res.body || []));
      return;
    }

    this.topUpService.query().subscribe((res: HttpResponse<ITopUp[]>) => (this.topUps = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTopUps();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITopUp): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTopUps(): void {
    this.eventSubscriber = this.eventManager.subscribe('topUpListModification', () => this.loadAll());
  }

  delete(topUp: ITopUp): void {
    const modalRef = this.modalService.open(TopUpDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.topUp = topUp;
  }
}
