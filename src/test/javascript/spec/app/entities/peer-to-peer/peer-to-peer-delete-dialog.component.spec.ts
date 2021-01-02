import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { PeerToPeerDeleteDialogComponent } from 'app/entities/peer-to-peer/peer-to-peer-delete-dialog.component';
import { PeerToPeerService } from 'app/entities/peer-to-peer/peer-to-peer.service';

describe('Component Tests', () => {
  describe('PeerToPeer Management Delete Component', () => {
    let comp: PeerToPeerDeleteDialogComponent;
    let fixture: ComponentFixture<PeerToPeerDeleteDialogComponent>;
    let service: PeerToPeerService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [PeerToPeerDeleteDialogComponent],
      })
        .overrideTemplate(PeerToPeerDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PeerToPeerDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PeerToPeerService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
