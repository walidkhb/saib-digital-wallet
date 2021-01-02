import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IKyc, Kyc } from 'app/shared/model/kyc.model';
import { KycService } from './kyc.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-kyc-update',
  templateUrl: './kyc-update.component.html',
})
export class KycUpdateComponent implements OnInit {
  isSaving = false;
  kycinfos: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    kycInfo: [],
  });

  constructor(
    protected kycService: KycService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kyc }) => {
      this.updateForm(kyc);

      this.customerService
        .query({ filter: 'kyc-is-null' })
        .pipe(
          map((res: HttpResponse<ICustomer[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICustomer[]) => {
          if (!kyc.kycInfo || !kyc.kycInfo.id) {
            this.kycinfos = resBody;
          } else {
            this.customerService
              .find(kyc.kycInfo.id)
              .pipe(
                map((subRes: HttpResponse<ICustomer>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICustomer[]) => (this.kycinfos = concatRes));
          }
        });
    });
  }

  updateForm(kyc: IKyc): void {
    this.editForm.patchValue({
      id: kyc.id,
      kycInfo: kyc.kycInfo,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kyc = this.createFromForm();
    if (kyc.id !== undefined) {
      this.subscribeToSaveResponse(this.kycService.update(kyc));
    } else {
      this.subscribeToSaveResponse(this.kycService.create(kyc));
    }
  }

  private createFromForm(): IKyc {
    return {
      ...new Kyc(),
      id: this.editForm.get(['id'])!.value,
      kycInfo: this.editForm.get(['kycInfo'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKyc>>): void {
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

  trackById(index: number, item: ICustomer): any {
    return item.id;
  }
}
