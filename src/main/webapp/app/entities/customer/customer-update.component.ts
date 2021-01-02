import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICustomer, Customer } from 'app/shared/model/customer.model';
import { CustomerService } from './customer.service';
import { IWallet } from 'app/shared/model/wallet.model';
import { WalletService } from 'app/entities/wallet/wallet.service';

@Component({
  selector: 'jhi-customer-update',
  templateUrl: './customer-update.component.html',
})
export class CustomerUpdateComponent implements OnInit {
  isSaving = false;
  wallets: IWallet[] = [];
  dateOfBirthDp: any;

  editForm = this.fb.group({
    id: [],
    nationalIdentityNumber: [],
    idType: [],
    dateOfBirth: [],
    mobilePhoneNumber: [null, [Validators.pattern('[+]\\d+-\\d+')]],
    agentVerificationNumber: [],
    email: [null, [Validators.pattern('[^@]+@[^\\.]+\\..+$')]],
    language: [],
    name: [],
    customerNumber: [],
    firstNameArabic: [],
    fatherNameArabic: [],
    grandFatherNameArabic: [],
    grandFatherNameEnglish: [],
    placeOfBirth: [],
    idIssueDate: [],
    idExpiryDate: [],
    maritalStatus: [],
    customerId: [],
    profileStatus: [],
    wallet: [],
  });

  constructor(
    protected customerService: CustomerService,
    protected walletService: WalletService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customer }) => {
      this.updateForm(customer);

      this.walletService.query().subscribe((res: HttpResponse<IWallet[]>) => (this.wallets = res.body || []));
    });
  }

  updateForm(customer: ICustomer): void {
    this.editForm.patchValue({
      id: customer.id,
      nationalIdentityNumber: customer.nationalIdentityNumber,
      idType: customer.idType,
      dateOfBirth: customer.dateOfBirth,
      mobilePhoneNumber: customer.mobilePhoneNumber,
      agentVerificationNumber: customer.agentVerificationNumber,
      email: customer.email,
      language: customer.language,
      name: customer.name,
      customerNumber: customer.customerNumber,
      firstNameArabic: customer.firstNameArabic,
      fatherNameArabic: customer.fatherNameArabic,
      grandFatherNameArabic: customer.grandFatherNameArabic,
      grandFatherNameEnglish: customer.grandFatherNameEnglish,
      placeOfBirth: customer.placeOfBirth,
      idIssueDate: customer.idIssueDate,
      idExpiryDate: customer.idExpiryDate,
      maritalStatus: customer.maritalStatus,
      customerId: customer.customerId,
      profileStatus: customer.profileStatus,
      wallet: customer.wallet,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customer = this.createFromForm();
    if (customer.id !== undefined) {
      this.subscribeToSaveResponse(this.customerService.update(customer));
    } else {
      this.subscribeToSaveResponse(this.customerService.create(customer));
    }
  }

  private createFromForm(): ICustomer {
    return {
      ...new Customer(),
      id: this.editForm.get(['id'])!.value,
      nationalIdentityNumber: this.editForm.get(['nationalIdentityNumber'])!.value,
      idType: this.editForm.get(['idType'])!.value,
      dateOfBirth: this.editForm.get(['dateOfBirth'])!.value,
      mobilePhoneNumber: this.editForm.get(['mobilePhoneNumber'])!.value,
      agentVerificationNumber: this.editForm.get(['agentVerificationNumber'])!.value,
      email: this.editForm.get(['email'])!.value,
      language: this.editForm.get(['language'])!.value,
      name: this.editForm.get(['name'])!.value,
      customerNumber: this.editForm.get(['customerNumber'])!.value,
      firstNameArabic: this.editForm.get(['firstNameArabic'])!.value,
      fatherNameArabic: this.editForm.get(['fatherNameArabic'])!.value,
      grandFatherNameArabic: this.editForm.get(['grandFatherNameArabic'])!.value,
      grandFatherNameEnglish: this.editForm.get(['grandFatherNameEnglish'])!.value,
      placeOfBirth: this.editForm.get(['placeOfBirth'])!.value,
      idIssueDate: this.editForm.get(['idIssueDate'])!.value,
      idExpiryDate: this.editForm.get(['idExpiryDate'])!.value,
      maritalStatus: this.editForm.get(['maritalStatus'])!.value,
      customerId: this.editForm.get(['customerId'])!.value,
      profileStatus: this.editForm.get(['profileStatus'])!.value,
      wallet: this.editForm.get(['wallet'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomer>>): void {
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
