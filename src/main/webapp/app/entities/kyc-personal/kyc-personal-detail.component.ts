import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKycPersonal } from 'app/shared/model/kyc-personal.model';

@Component({
  selector: 'jhi-kyc-personal-detail',
  templateUrl: './kyc-personal-detail.component.html',
})
export class KycPersonalDetailComponent implements OnInit {
  kycPersonal: IKycPersonal | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kycPersonal }) => (this.kycPersonal = kycPersonal));
  }

  previousState(): void {
    window.history.back();
  }
}
