import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKyc } from 'app/shared/model/kyc.model';

@Component({
  selector: 'jhi-kyc-detail',
  templateUrl: './kyc-detail.component.html',
})
export class KycDetailComponent implements OnInit {
  kyc: IKyc | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kyc }) => (this.kyc = kyc));
  }

  previousState(): void {
    window.history.back();
  }
}
