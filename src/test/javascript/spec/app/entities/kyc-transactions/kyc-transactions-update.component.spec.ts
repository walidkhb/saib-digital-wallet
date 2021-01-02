import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { KycTransactionsUpdateComponent } from 'app/entities/kyc-transactions/kyc-transactions-update.component';
import { KycTransactionsService } from 'app/entities/kyc-transactions/kyc-transactions.service';
import { KycTransactions } from 'app/shared/model/kyc-transactions.model';

describe('Component Tests', () => {
  describe('KycTransactions Management Update Component', () => {
    let comp: KycTransactionsUpdateComponent;
    let fixture: ComponentFixture<KycTransactionsUpdateComponent>;
    let service: KycTransactionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [KycTransactionsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(KycTransactionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KycTransactionsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KycTransactionsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new KycTransactions(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new KycTransactions();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
