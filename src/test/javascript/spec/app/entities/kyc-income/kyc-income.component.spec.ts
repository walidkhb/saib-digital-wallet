import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { KycIncomeComponent } from 'app/entities/kyc-income/kyc-income.component';
import { KycIncomeService } from 'app/entities/kyc-income/kyc-income.service';
import { KycIncome } from 'app/shared/model/kyc-income.model';

describe('Component Tests', () => {
  describe('KycIncome Management Component', () => {
    let comp: KycIncomeComponent;
    let fixture: ComponentFixture<KycIncomeComponent>;
    let service: KycIncomeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [KycIncomeComponent],
      })
        .overrideTemplate(KycIncomeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KycIncomeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KycIncomeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new KycIncome(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.kycIncomes && comp.kycIncomes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
