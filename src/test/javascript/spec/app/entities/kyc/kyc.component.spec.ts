import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { KycComponent } from 'app/entities/kyc/kyc.component';
import { KycService } from 'app/entities/kyc/kyc.service';
import { Kyc } from 'app/shared/model/kyc.model';

describe('Component Tests', () => {
  describe('Kyc Management Component', () => {
    let comp: KycComponent;
    let fixture: ComponentFixture<KycComponent>;
    let service: KycService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [KycComponent],
      })
        .overrideTemplate(KycComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KycComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KycService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Kyc(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.kycs && comp.kycs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
