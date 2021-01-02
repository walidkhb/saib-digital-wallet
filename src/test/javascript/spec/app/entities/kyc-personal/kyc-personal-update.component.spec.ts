import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { KycPersonalUpdateComponent } from 'app/entities/kyc-personal/kyc-personal-update.component';
import { KycPersonalService } from 'app/entities/kyc-personal/kyc-personal.service';
import { KycPersonal } from 'app/shared/model/kyc-personal.model';

describe('Component Tests', () => {
  describe('KycPersonal Management Update Component', () => {
    let comp: KycPersonalUpdateComponent;
    let fixture: ComponentFixture<KycPersonalUpdateComponent>;
    let service: KycPersonalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [KycPersonalUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(KycPersonalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KycPersonalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KycPersonalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new KycPersonal(123);
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
        const entity = new KycPersonal();
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
