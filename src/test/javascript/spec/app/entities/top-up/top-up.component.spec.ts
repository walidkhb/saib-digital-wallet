import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { TopUpComponent } from 'app/entities/top-up/top-up.component';
import { TopUpService } from 'app/entities/top-up/top-up.service';
import { TopUp } from 'app/shared/model/top-up.model';

describe('Component Tests', () => {
  describe('TopUp Management Component', () => {
    let comp: TopUpComponent;
    let fixture: ComponentFixture<TopUpComponent>;
    let service: TopUpService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [TopUpComponent],
      })
        .overrideTemplate(TopUpComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TopUpComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TopUpService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TopUp(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.topUps && comp.topUps[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
