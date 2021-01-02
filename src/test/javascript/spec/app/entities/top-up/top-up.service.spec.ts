import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TopUpService } from 'app/entities/top-up/top-up.service';
import { ITopUp, TopUp } from 'app/shared/model/top-up.model';

describe('Service Tests', () => {
  describe('TopUp Service', () => {
    let injector: TestBed;
    let service: TopUpService;
    let httpMock: HttpTestingController;
    let elemDefault: ITopUp;
    let expectedResult: ITopUp | ITopUp[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TopUpService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TopUp(0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TopUp', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TopUp()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TopUp', () => {
        const returnedFromService = Object.assign(
          {
            amount: 1,
            currency: 'BBBBBB',
            transactionType: 'BBBBBB',
            narativeLine1: 'BBBBBB',
            narativeLine2: 'BBBBBB',
            narativeLine3: 'BBBBBB',
            narativeLine4: 'BBBBBB',
            clientRefNumber: 'BBBBBB',
            paymentDetails: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TopUp', () => {
        const returnedFromService = Object.assign(
          {
            amount: 1,
            currency: 'BBBBBB',
            transactionType: 'BBBBBB',
            narativeLine1: 'BBBBBB',
            narativeLine2: 'BBBBBB',
            narativeLine3: 'BBBBBB',
            narativeLine4: 'BBBBBB',
            clientRefNumber: 'BBBBBB',
            paymentDetails: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TopUp', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
