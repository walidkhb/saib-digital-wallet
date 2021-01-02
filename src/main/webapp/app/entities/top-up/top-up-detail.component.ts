import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITopUp } from 'app/shared/model/top-up.model';

@Component({
  selector: 'jhi-top-up-detail',
  templateUrl: './top-up-detail.component.html',
})
export class TopUpDetailComponent implements OnInit {
  topUp: ITopUp | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ topUp }) => (this.topUp = topUp));
  }

  previousState(): void {
    window.history.back();
  }
}
