import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { KycDetailComponent } from 'app/entities/kyc/kyc-detail.component';
import { Kyc } from 'app/shared/model/kyc.model';

describe('Component Tests', () => {
  describe('Kyc Management Detail Component', () => {
    let comp: KycDetailComponent;
    let fixture: ComponentFixture<KycDetailComponent>;
    const route = ({ data: of({ kyc: new Kyc(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [KycDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(KycDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(KycDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load kyc on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.kyc).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
