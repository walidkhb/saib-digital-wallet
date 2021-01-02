import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IKycTransactions, KycTransactions } from 'app/shared/model/kyc-transactions.model';
import { KycTransactionsService } from './kyc-transactions.service';
import { IKyc } from 'app/shared/model/kyc.model';
import { KycService } from 'app/entities/kyc/kyc.service';

@Component({
  selector: 'jhi-kyc-transactions-update',
  templateUrl: './kyc-transactions-update.component.html',
})
export class KycTransactionsUpdateComponent implements OnInit {
  isSaving = false;
  kyctransactions: IKyc[] = [];

  editForm = this.fb.group({
    id: [],
    creditCount: [],
    creditAmount: [],
    debitCount: [],
    debitAmount: [],
    remittanceCount: [],
    remittanceAmount: [],
    kycTransactions: [],
  });

  constructor(
    protected kycTransactionsService: KycTransactionsService,
    protected kycService: KycService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kycTransactions }) => {
      this.updateForm(kycTransactions);

      this.kycService
        .query({ filter: 'kyctransactions-is-null' })
        .pipe(
          map((res: HttpResponse<IKyc[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IKyc[]) => {
          if (!kycTransactions.kycTransactions || !kycTransactions.kycTransactions.id) {
            this.kyctransactions = resBody;
          } else {
            this.kycService
              .find(kycTransactions.kycTransactions.id)
              .pipe(
                map((subRes: HttpResponse<IKyc>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IKyc[]) => (this.kyctransactions = concatRes));
          }
        });
    });
  }

  updateForm(kycTransactions: IKycTransactions): void {
    this.editForm.patchValue({
      id: kycTransactions.id,
      creditCount: kycTransactions.creditCount,
      creditAmount: kycTransactions.creditAmount,
      debitCount: kycTransactions.debitCount,
      debitAmount: kycTransactions.debitAmount,
      remittanceCount: kycTransactions.remittanceCount,
      remittanceAmount: kycTransactions.remittanceAmount,
      kycTransactions: kycTransactions.kycTransactions,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kycTransactions = this.createFromForm();
    if (kycTransactions.id !== undefined) {
      this.subscribeToSaveResponse(this.kycTransactionsService.update(kycTransactions));
    } else {
      this.subscribeToSaveResponse(this.kycTransactionsService.create(kycTransactions));
    }
  }

  private createFromForm(): IKycTransactions {
    return {
      ...new KycTransactions(),
      id: this.editForm.get(['id'])!.value,
      creditCount: this.editForm.get(['creditCount'])!.value,
      creditAmount: this.editForm.get(['creditAmount'])!.value,
      debitCount: this.editForm.get(['debitCount'])!.value,
      debitAmount: this.editForm.get(['debitAmount'])!.value,
      remittanceCount: this.editForm.get(['remittanceCount'])!.value,
      remittanceAmount: this.editForm.get(['remittanceAmount'])!.value,
      kycTransactions: this.editForm.get(['kycTransactions'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKycTransactions>>): void {
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
