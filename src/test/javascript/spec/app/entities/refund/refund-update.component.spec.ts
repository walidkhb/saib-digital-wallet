import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { RefundUpdateComponent } from 'app/entities/refund/refund-update.component';
import { RefundService } from 'app/entities/refund/refund.service';
import { Refund } from 'app/shared/model/refund.model';

describe('Component Tests', () => {
  describe('Refund Management Update Component', () => {
    let comp: RefundUpdateComponent;
    let fixture: ComponentFixture<RefundUpdateComponent>;
    let service: RefundService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [RefundUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RefundUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RefundUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RefundService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Refund(123);
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
        const entity = new Refund();
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
