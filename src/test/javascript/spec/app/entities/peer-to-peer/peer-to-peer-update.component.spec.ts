import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { PeerToPeerUpdateComponent } from 'app/entities/peer-to-peer/peer-to-peer-update.component';
import { PeerToPeerService } from 'app/entities/peer-to-peer/peer-to-peer.service';
import { PeerToPeer } from 'app/shared/model/peer-to-peer.model';

describe('Component Tests', () => {
  describe('PeerToPeer Management Update Component', () => {
    let comp: PeerToPeerUpdateComponent;
    let fixture: ComponentFixture<PeerToPeerUpdateComponent>;
    let service: PeerToPeerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [PeerToPeerUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PeerToPeerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PeerToPeerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PeerToPeerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PeerToPeer(123);
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
        const entity = new PeerToPeer();
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
