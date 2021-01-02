import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPeerToPeer } from 'app/shared/model/peer-to-peer.model';

@Component({
  selector: 'jhi-peer-to-peer-detail',
  templateUrl: './peer-to-peer-detail.component.html',
})
export class PeerToPeerDetailComponent implements OnInit {
  peerToPeer: IPeerToPeer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ peerToPeer }) => (this.peerToPeer = peerToPeer));
  }

  previousState(): void {
    window.history.back();
  }
}
