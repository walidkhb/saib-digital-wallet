import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { PeerToPeerComponent } from 'app/entities/peer-to-peer/peer-to-peer.component';
import { PeerToPeerService } from 'app/entities/peer-to-peer/peer-to-peer.service';
import { PeerToPeer } from 'app/shared/model/peer-to-peer.model';

describe('Component Tests', () => {
  describe('PeerToPeer Management Component', () => {
    let comp: PeerToPeerComponent;
    let fixture: ComponentFixture<PeerToPeerComponent>;
    let service: PeerToPeerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [PeerToPeerComponent],
      })
        .overrideTemplate(PeerToPeerComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PeerToPeerComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PeerToPeerService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PeerToPeer(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.peerToPeers && comp.peerToPeers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
