import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { KycPersonalDetailComponent } from 'app/entities/kyc-personal/kyc-personal-detail.component';
import { KycPersonal } from 'app/shared/model/kyc-personal.model';

describe('Component Tests', () => {
  describe('KycPersonal Management Detail Component', () => {
    let comp: KycPersonalDetailComponent;
    let fixture: ComponentFixture<KycPersonalDetailComponent>;
    const route = ({ data: of({ kycPersonal: new KycPersonal(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [KycPersonalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(KycPersonalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(KycPersonalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load kycPersonal on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.kycPersonal).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
