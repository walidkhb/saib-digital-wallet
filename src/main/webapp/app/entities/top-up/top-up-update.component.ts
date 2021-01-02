import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITopUp, TopUp } from 'app/shared/model/top-up.model';
import { TopUpService } from './top-up.service';
import { IWallet } from 'app/shared/model/wallet.model';
import { WalletService } from 'app/entities/wallet/wallet.service';

@Component({
  selector: 'jhi-top-up-update',
  templateUrl: './top-up-update.component.html',
})
export class TopUpUpdateComponent implements OnInit {
  isSaving = false;
  wallets: IWallet[] = [];

  editForm = this.fb.group({
    id: [],
    amount: [],
    currency: [],
    transactionType: [],
    narativeLine1: [],
    narativeLine2: [],
    narativeLine3: [],
    narativeLine4: [],
    clientRefNumber: [],
    paymentDetails: [],
    topup: [],
  });

  constructor(
    protected topUpService: TopUpService,
    protected walletService: WalletService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ topUp }) => {
      this.updateForm(topUp);

      this.walletService.query().subscribe((res: HttpResponse<IWallet[]>) => (this.wallets = res.body || []));
    });
  }

  updateForm(topUp: ITopUp): void {
    this.editForm.patchValue({
      id: topUp.id,
      amount: topUp.amount,
      currency: topUp.currency,
      transactionType: topUp.transactionType,
      narativeLine1: topUp.narativeLine1,
      narativeLine2: topUp.narativeLine2,
      narativeLine3: topUp.narativeLine3,
      narativeLine4: topUp.narativeLine4,
      clientRefNumber: topUp.clientRefNumber,
      paymentDetails: topUp.paymentDetails,
      topup: topUp.topup,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const topUp = this.createFromForm();
    if (topUp.id !== undefined) {
      this.subscribeToSaveResponse(this.topUpService.update(topUp));
    } else {
      this.subscribeToSaveResponse(this.topUpService.create(topUp));
    }
  }

  private createFromForm(): ITopUp {
    return {
      ...new TopUp(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      currency: this.editForm.get(['currency'])!.value,
      transactionType: this.editForm.get(['transactionType'])!.value,
      narativeLine1: this.editForm.get(['narativeLine1'])!.value,
      narativeLine2: this.editForm.get(['narativeLine2'])!.value,
      narativeLine3: this.editForm.get(['narativeLine3'])!.value,
      narativeLine4: this.editForm.get(['narativeLine4'])!.value,
      clientRefNumber: this.editForm.get(['clientRefNumber'])!.value,
      paymentDetails: this.editForm.get(['paymentDetails'])!.value,
      topup: this.editForm.get(['topup'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITopUp>>): void {
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

  trackById(index: number, item: IWallet): any {
    return item.id;
  }
}
