import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { TopUpUpdateComponent } from 'app/entities/top-up/top-up-update.component';
import { TopUpService } from 'app/entities/top-up/top-up.service';
import { TopUp } from 'app/shared/model/top-up.model';

describe('Component Tests', () => {
  describe('TopUp Management Update Component', () => {
    let comp: TopUpUpdateComponent;
    let fixture: ComponentFixture<TopUpUpdateComponent>;
    let service: TopUpService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [TopUpUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TopUpUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TopUpUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TopUpService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TopUp(123);
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
        const entity = new TopUp();
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
