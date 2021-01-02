import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPeerToPeer, PeerToPeer } from 'app/shared/model/peer-to-peer.model';
import { PeerToPeerService } from './peer-to-peer.service';
import { IWallet } from 'app/shared/model/wallet.model';
import { WalletService } from 'app/entities/wallet/wallet.service';

@Component({
  selector: 'jhi-peer-to-peer-update',
  templateUrl: './peer-to-peer-update.component.html',
})
export class PeerToPeerUpdateComponent implements OnInit {
  isSaving = false;
  wallets: IWallet[] = [];

  editForm = this.fb.group({
    id: [],
    amount: [],
    currency: [],
    beneficiaryRelationship: [],
    purposeOfTransfer: [],
    transactionType: [],
    paymentDetails: [],
    peertopeer: [],
  });

  constructor(
    protected peerToPeerService: PeerToPeerService,
    protected walletService: WalletService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ peerToPeer }) => {
      this.updateForm(peerToPeer);

      this.walletService.query().subscribe((res: HttpResponse<IWallet[]>) => (this.wallets = res.body || []));
    });
  }

  updateForm(peerToPeer: IPeerToPeer): void {
    this.editForm.patchValue({
      id: peerToPeer.id,
      amount: peerToPeer.amount,
      currency: peerToPeer.currency,
      beneficiaryRelationship: peerToPeer.beneficiaryRelationship,
      purposeOfTransfer: peerToPeer.purposeOfTransfer,
      transactionType: peerToPeer.transactionType,
      paymentDetails: peerToPeer.paymentDetails,
      peertopeer: peerToPeer.peertopeer,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const peerToPeer = this.createFromForm();
    if (peerToPeer.id !== undefined) {
      this.subscribeToSaveResponse(this.peerToPeerService.update(peerToPeer));
    } else {
      this.subscribeToSaveResponse(this.peerToPeerService.create(peerToPeer));
    }
  }

  private createFromForm(): IPeerToPeer {
    return {
      ...new PeerToPeer(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      currency: this.editForm.get(['currency'])!.value,
      beneficiaryRelationship: this.editForm.get(['beneficiaryRelationship'])!.value,
      purposeOfTransfer: this.editForm.get(['purposeOfTransfer'])!.value,
      transactionType: this.editForm.get(['transactionType'])!.value,
      paymentDetails: this.editForm.get(['paymentDetails'])!.value,
      peertopeer: this.editForm.get(['peertopeer'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPeerToPeer>>): void {
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
