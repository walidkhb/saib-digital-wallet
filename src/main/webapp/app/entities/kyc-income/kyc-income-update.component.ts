import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IKycIncome, KycIncome } from 'app/shared/model/kyc-income.model';
import { KycIncomeService } from './kyc-income.service';
import { IKyc } from 'app/shared/model/kyc.model';
import { KycService } from 'app/entities/kyc/kyc.service';

@Component({
  selector: 'jhi-kyc-income-update',
  templateUrl: './kyc-income-update.component.html',
})
export class KycIncomeUpdateComponent implements OnInit {
  isSaving = false;
  kycincomes: IKyc[] = [];

  editForm = this.fb.group({
    id: [],
    primarySource: [],
    primaryAmount: [],
    pecondarySource: [],
    pecondaryAmount: [],
    kycIncome: [],
  });

  constructor(
    protected kycIncomeService: KycIncomeService,
    protected kycService: KycService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kycIncome }) => {
      this.updateForm(kycIncome);

      this.kycService
        .query({ filter: 'kycincome-is-null' })
        .pipe(
          map((res: HttpResponse<IKyc[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IKyc[]) => {
          if (!kycIncome.kycIncome || !kycIncome.kycIncome.id) {
            this.kycincomes = resBody;
          } else {
            this.kycService
              .find(kycIncome.kycIncome.id)
              .pipe(
                map((subRes: HttpResponse<IKyc>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IKyc[]) => (this.kycincomes = concatRes));
          }
        });
    });
  }

  updateForm(kycIncome: IKycIncome): void {
    this.editForm.patchValue({
      id: kycIncome.id,
      primarySource: kycIncome.primarySource,
      primaryAmount: kycIncome.primaryAmount,
      pecondarySource: kycIncome.pecondarySource,
      pecondaryAmount: kycIncome.pecondaryAmount,
      kycIncome: kycIncome.kycIncome,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kycIncome = this.createFromForm();
    if (kycIncome.id !== undefined) {
      this.subscribeToSaveResponse(this.kycIncomeService.update(kycIncome));
    } else {
      this.subscribeToSaveResponse(this.kycIncomeService.create(kycIncome));
    }
  }

  private createFromForm(): IKycIncome {
    return {
      ...new KycIncome(),
      id: this.editForm.get(['id'])!.value,
      primarySource: this.editForm.get(['primarySource'])!.value,
      primaryAmount: this.editForm.get(['primaryAmount'])!.value,
      pecondarySource: this.editForm.get(['pecondarySource'])!.value,
      pecondaryAmount: this.editForm.get(['pecondaryAmount'])!.value,
      kycIncome: this.editForm.get(['kycIncome'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKycIncome>>): void {
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
