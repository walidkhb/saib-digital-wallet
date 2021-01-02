import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { PeerToPeerDetailComponent } from 'app/entities/peer-to-peer/peer-to-peer-detail.component';
import { PeerToPeer } from 'app/shared/model/peer-to-peer.model';

describe('Component Tests', () => {
  describe('PeerToPeer Management Detail Component', () => {
    let comp: PeerToPeerDetailComponent;
    let fixture: ComponentFixture<PeerToPeerDetailComponent>;
    const route = ({ data: of({ peerToPeer: new PeerToPeer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [PeerToPeerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PeerToPeerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PeerToPeerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load peerToPeer on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.peerToPeer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
