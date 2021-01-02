import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKycTransactions } from 'app/shared/model/kyc-transactions.model';

@Component({
  selector: 'jhi-kyc-transactions-detail',
  templateUrl: './kyc-transactions-detail.component.html',
})
export class KycTransactionsDetailComponent implements OnInit {
  kycTransactions: IKycTransactions | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kycTransactions }) => (this.kycTransactions = kycTransactions));
  }

  previousState(): void {
    window.history.back();
  }
}
