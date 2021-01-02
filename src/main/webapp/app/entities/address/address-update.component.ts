import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IAddress, Address } from 'app/shared/model/address.model';
import { AddressService } from './address.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-address-update',
  templateUrl: './address-update.component.html',
})
export class AddressUpdateComponent implements OnInit {
  isSaving = false;
  addresses: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    buildingNumber: [],
    streetName: [],
    neighborhood: [],
    cityName: [],
    zipCode: [],
    additionalNumber: [],
    regionDescription: [],
    unitNumber: [],
    address: [],
  });

  constructor(
    protected addressService: AddressService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ address }) => {
      this.updateForm(address);

      this.customerService
        .query({ filter: 'address-is-null' })
        .pipe(
          map((res: HttpResponse<ICustomer[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICustomer[]) => {
          if (!address.address || !address.address.id) {
            this.addresses = resBody;
          } else {
            this.customerService
              .find(address.address.id)
              .pipe(
                map((subRes: HttpResponse<ICustomer>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICustomer[]) => (this.addresses = concatRes));
          }
        });
    });
  }

  updateForm(address: IAddress): void {
    this.editForm.patchValue({
      id: address.id,
      buildingNumber: address.buildingNumber,
      streetName: address.streetName,
      neighborhood: address.neighborhood,
      cityName: address.cityName,
      zipCode: address.zipCode,
      additionalNumber: address.additionalNumber,
      regionDescription: address.regionDescription,
      unitNumber: address.unitNumber,
      address: address.address,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const address = this.createFromForm();
    if (address.id !== undefined) {
      this.subscribeToSaveResponse(this.addressService.update(address));
    } else {
      this.subscribeToSaveResponse(this.addressService.create(address));
    }
  }

  private createFromForm(): IAddress {
    return {
      ...new Address(),
      id: this.editForm.get(['id'])!.value,
      buildingNumber: this.editForm.get(['buildingNumber'])!.value,
      streetName: this.editForm.get(['streetName'])!.value,
      neighborhood: this.editForm.get(['neighborhood'])!.value,
      cityName: this.editForm.get(['cityName'])!.value,
      zipCode: this.editForm.get(['zipCode'])!.value,
      additionalNumber: this.editForm.get(['additionalNumber'])!.value,
      regionDescription: this.editForm.get(['regionDescription'])!.value,
      unitNumber: this.editForm.get(['unitNumber'])!.value,
      address: this.editForm.get(['address'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddress>>): void {
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
