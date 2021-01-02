import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { KycIncomeUpdateComponent } from 'app/entities/kyc-income/kyc-income-update.component';
import { KycIncomeService } from 'app/entities/kyc-income/kyc-income.service';
import { KycIncome } from 'app/shared/model/kyc-income.model';

describe('Component Tests', () => {
  describe('KycIncome Management Update Component', () => {
    let comp: KycIncomeUpdateComponent;
    let fixture: ComponentFixture<KycIncomeUpdateComponent>;
    let service: KycIncomeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [KycIncomeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(KycIncomeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KycIncomeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KycIncomeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new KycIncome(123);
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
        const entity = new KycIncome();
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
