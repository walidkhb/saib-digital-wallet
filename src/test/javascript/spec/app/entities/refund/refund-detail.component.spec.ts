import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { RefundDetailComponent } from 'app/entities/refund/refund-detail.component';
import { Refund } from 'app/shared/model/refund.model';

describe('Component Tests', () => {
  describe('Refund Management Detail Component', () => {
    let comp: RefundDetailComponent;
    let fixture: ComponentFixture<RefundDetailComponent>;
    const route = ({ data: of({ refund: new Refund(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [RefundDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RefundDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RefundDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load refund on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.refund).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
