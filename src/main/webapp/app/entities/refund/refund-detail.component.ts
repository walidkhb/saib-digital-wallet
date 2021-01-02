import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRefund } from 'app/shared/model/refund.model';

@Component({
  selector: 'jhi-refund-detail',
  templateUrl: './refund-detail.component.html',
})
export class RefundDetailComponent implements OnInit {
  refund: IRefund | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ refund }) => (this.refund = refund));
  }

  previousState(): void {
    window.history.back();
  }
}
