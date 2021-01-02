import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPeerToPeer } from 'app/shared/model/peer-to-peer.model';
import { PeerToPeerService } from './peer-to-peer.service';
import { PeerToPeerDeleteDialogComponent } from './peer-to-peer-delete-dialog.component';

@Component({
  selector: 'jhi-peer-to-peer',
  templateUrl: './peer-to-peer.component.html',
})
export class PeerToPeerComponent implements OnInit, OnDestroy {
  peerToPeers?: IPeerToPeer[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected peerToPeerService: PeerToPeerService,
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
      this.peerToPeerService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IPeerToPeer[]>) => (this.peerToPeers = res.body || []));
      return;
    }

    this.peerToPeerService.query().subscribe((res: HttpResponse<IPeerToPeer[]>) => (this.peerToPeers = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPeerToPeers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPeerToPeer): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPeerToPeers(): void {
    this.eventSubscriber = this.eventManager.subscribe('peerToPeerListModification', () => this.loadAll());
  }

  delete(peerToPeer: IPeerToPeer): void {
    const modalRef = this.modalService.open(PeerToPeerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.peerToPeer = peerToPeer;
  }
}
