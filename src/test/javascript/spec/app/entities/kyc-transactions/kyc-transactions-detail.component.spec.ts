import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { KycTransactionsDetailComponent } from 'app/entities/kyc-transactions/kyc-transactions-detail.component';
import { KycTransactions } from 'app/shared/model/kyc-transactions.model';

describe('Component Tests', () => {
  describe('KycTransactions Management Detail Component', () => {
    let comp: KycTransactionsDetailComponent;
    let fixture: ComponentFixture<KycTransactionsDetailComponent>;
    const route = ({ data: of({ kycTransactions: new KycTransactions(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [KycTransactionsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(KycTransactionsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(KycTransactionsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load kycTransactions on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.kycTransactions).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
