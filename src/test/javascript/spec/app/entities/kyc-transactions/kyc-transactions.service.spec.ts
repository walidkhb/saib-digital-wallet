import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { KycTransactionsService } from 'app/entities/kyc-transactions/kyc-transactions.service';
import { IKycTransactions, KycTransactions } from 'app/shared/model/kyc-transactions.model';

describe('Service Tests', () => {
  describe('KycTransactions Service', () => {
    let injector: TestBed;
    let service: KycTransactionsService;
    let httpMock: HttpTestingController;
    let elemDefault: IKycTransactions;
    let expectedResult: IKycTransactions | IKycTransactions[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(KycTransactionsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new KycTransactions(0, 0, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a KycTransactions', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new KycTransactions()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a KycTransactions', () => {
        const returnedFromService = Object.assign(
          {
            creditCount: 1,
            creditAmount: 1,
            debitCount: 1,
            debitAmount: 1,
            remittanceCount: 1,
            remittanceAmount: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of KycTransactions', () => {
        const returnedFromService = Object.assign(
          {
            creditCount: 1,
            creditAmount: 1,
            debitCount: 1,
            debitAmount: 1,
            remittanceCount: 1,
            remittanceAmount: 1,
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

      it('should delete a KycTransactions', () => {
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
