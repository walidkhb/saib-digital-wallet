import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { KycPersonalComponent } from 'app/entities/kyc-personal/kyc-personal.component';
import { KycPersonalService } from 'app/entities/kyc-personal/kyc-personal.service';
import { KycPersonal } from 'app/shared/model/kyc-personal.model';

describe('Component Tests', () => {
  describe('KycPersonal Management Component', () => {
    let comp: KycPersonalComponent;
    let fixture: ComponentFixture<KycPersonalComponent>;
    let service: KycPersonalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [KycPersonalComponent],
      })
        .overrideTemplate(KycPersonalComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KycPersonalComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KycPersonalService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new KycPersonal(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.kycPersonals && comp.kycPersonals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
