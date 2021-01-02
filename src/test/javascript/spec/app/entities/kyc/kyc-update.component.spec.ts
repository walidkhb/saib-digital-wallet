import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { KycUpdateComponent } from 'app/entities/kyc/kyc-update.component';
import { KycService } from 'app/entities/kyc/kyc.service';
import { Kyc } from 'app/shared/model/kyc.model';

describe('Component Tests', () => {
  describe('Kyc Management Update Component', () => {
    let comp: KycUpdateComponent;
    let fixture: ComponentFixture<KycUpdateComponent>;
    let service: KycService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [KycUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(KycUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KycUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KycService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Kyc(123);
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
        const entity = new Kyc();
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
