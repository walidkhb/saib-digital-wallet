import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IKycPersonal, KycPersonal } from 'app/shared/model/kyc-personal.model';
import { KycPersonalService } from './kyc-personal.service';
import { IKyc } from 'app/shared/model/kyc.model';
import { KycService } from 'app/entities/kyc/kyc.service';

@Component({
  selector: 'jhi-kyc-personal-update',
  templateUrl: './kyc-personal-update.component.html',
})
export class KycPersonalUpdateComponent implements OnInit {
  isSaving = false;
  kycs: IKyc[] = [];

  editForm = this.fb.group({
    id: [],
    primarySource: [],
    primaryAmount: [],
    pecondarySource: [],
    pecondaryAmount: [],
    kycPersonal: [],
  });

  constructor(
    protected kycPersonalService: KycPersonalService,
    protected kycService: KycService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kycPersonal }) => {
      this.updateForm(kycPersonal);

      this.kycService.query().subscribe((res: HttpResponse<IKyc[]>) => (this.kycs = res.body || []));
    });
  }

  updateForm(kycPersonal: IKycPersonal): void {
    this.editForm.patchValue({
      id: kycPersonal.id,
      primarySource: kycPersonal.primarySource,
      primaryAmount: kycPersonal.primaryAmount,
      pecondarySource: kycPersonal.pecondarySource,
      pecondaryAmount: kycPersonal.pecondaryAmount,
      kycPersonal: kycPersonal.kycPersonal,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kycPersonal = this.createFromForm();
    if (kycPersonal.id !== undefined) {
      this.subscribeToSaveResponse(this.kycPersonalService.update(kycPersonal));
    } else {
      this.subscribeToSaveResponse(this.kycPersonalService.create(kycPersonal));
    }
  }

  private createFromForm(): IKycPersonal {
    return {
      ...new KycPersonal(),
      id: this.editForm.get(['id'])!.value,
      primarySource: this.editForm.get(['primarySource'])!.value,
      primaryAmount: this.editForm.get(['primaryAmount'])!.value,
      pecondarySource: this.editForm.get(['pecondarySource'])!.value,
      pecondaryAmount: this.editForm.get(['pecondaryAmount'])!.value,
      kycPersonal: this.editForm.get(['kycPersonal'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKycPersonal>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IKyc): any {
    return item.id;
  }
}
