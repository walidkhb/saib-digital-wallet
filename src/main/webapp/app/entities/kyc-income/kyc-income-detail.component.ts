import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKycIncome } from 'app/shared/model/kyc-income.model';

@Component({
  selector: 'jhi-kyc-income-detail',
  templateUrl: './kyc-income-detail.component.html',
})
export class KycIncomeDetailComponent implements OnInit {
  kycIncome: IKycIncome | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kycIncome }) => (this.kycIncome = kycIncome));
  }

  previousState(): void {
    window.history.back();
  }
}
