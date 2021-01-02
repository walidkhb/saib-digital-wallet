import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaibDigitalWalletTestModule } from '../../../test.module';
import { TopUpDetailComponent } from 'app/entities/top-up/top-up-detail.component';
import { TopUp } from 'app/shared/model/top-up.model';

describe('Component Tests', () => {
  describe('TopUp Management Detail Component', () => {
    let comp: TopUpDetailComponent;
    let fixture: ComponentFixture<TopUpDetailComponent>;
    const route = ({ data: of({ topUp: new TopUp(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SaibDigitalWalletTestModule],
        declarations: [TopUpDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TopUpDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TopUpDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load topUp on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.topUp).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
