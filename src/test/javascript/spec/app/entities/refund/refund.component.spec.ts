import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { RefundComponent } from 'app/entities/refund/refund.component';
import { RefundService } from 'app/entities/refund/refund.service';
import { Refund } from 'app/shared/model/refund.model';

describe('Component Tests', () => {
  describe('Refund Management Component', () => {
    let comp: RefundComponent;
    let fixture: ComponentFixture<RefundComponent>;
    let service: RefundService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [RefundComponent],
      })
        .overrideTemplate(RefundComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RefundComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RefundService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Refund(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.refunds && comp.refunds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
