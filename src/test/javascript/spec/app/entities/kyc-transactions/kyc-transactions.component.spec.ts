import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { KycTransactionsComponent } from 'app/entities/kyc-transactions/kyc-transactions.component';
import { KycTransactionsService } from 'app/entities/kyc-transactions/kyc-transactions.service';
import { KycTransactions } from 'app/shared/model/kyc-transactions.model';

describe('Component Tests', () => {
  describe('KycTransactions Management Component', () => {
    let comp: KycTransactionsComponent;
    let fixture: ComponentFixture<KycTransactionsComponent>;
    let service: KycTransactionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [KycTransactionsComponent],
      })
        .overrideTemplate(KycTransactionsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KycTransactionsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KycTransactionsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new KycTransactions(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.kycTransactions && comp.kycTransactions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
