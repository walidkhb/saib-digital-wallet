import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRefund, Refund } from 'app/shared/model/refund.model';
import { RefundService } from './refund.service';

@Component({
  selector: 'jhi-refund-update',
  templateUrl: './refund-update.component.html',
})
export class RefundUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    amount: [],
    currency: [],
    narativeLine1: [],
    narativeLine2: [],
  });

  constructor(protected refundService: RefundService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ refund }) => {
      this.updateForm(refund);
    });
  }

  updateForm(refund: IRefund): void {
    this.editForm.patchValue({
      id: refund.id,
      amount: refund.amount,
      currency: refund.currency,
      narativeLine1: refund.narativeLine1,
      narativeLine2: refund.narativeLine2,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const refund = this.createFromForm();
    if (refund.id !== undefined) {
      this.subscribeToSaveResponse(this.refundService.update(refund));
    } else {
      this.subscribeToSaveResponse(this.refundService.create(refund));
    }
  }

  private createFromForm(): IRefund {
    return {
      ...new Refund(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      currency: this.editForm.get(['currency'])!.value,
      narativeLine1: this.editForm.get(['narativeLine1'])!.value,
      narativeLine2: this.editForm.get(['narativeLine2'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRefund>>): void {
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
}
