import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { KycIncomeDetailComponent } from 'app/entities/kyc-income/kyc-income-detail.component';
import { KycIncome } from 'app/shared/model/kyc-income.model';

describe('Component Tests', () => {
  describe('KycIncome Management Detail Component', () => {
    let comp: KycIncomeDetailComponent;
    let fixture: ComponentFixture<KycIncomeDetailComponent>;
    const route = ({ data: of({ kycIncome: new KycIncome(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [KycIncomeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(KycIncomeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(KycIncomeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load kycIncome on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.kycIncome).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
