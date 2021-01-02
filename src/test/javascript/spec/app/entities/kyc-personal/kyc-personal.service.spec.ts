import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { KycPersonalService } from 'app/entities/kyc-personal/kyc-personal.service';
import { IKycPersonal, KycPersonal } from 'app/shared/model/kyc-personal.model';

describe('Service Tests', () => {
  describe('KycPersonal Service', () => {
    let injector: TestBed;
    let service: KycPersonalService;
    let httpMock: HttpTestingController;
    let elemDefault: IKycPersonal;
    let expectedResult: IKycPersonal | IKycPersonal[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(KycPersonalService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new KycPersonal(0, 'AAAAAAA', 0, 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a KycPersonal', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new KycPersonal()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a KycPersonal', () => {
        const returnedFromService = Object.assign(
          {
            primarySource: 'BBBBBB',
            primaryAmount: 1,
            pecondarySource: 'BBBBBB',
            pecondaryAmount: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of KycPersonal', () => {
        const returnedFromService = Object.assign(
          {
            primarySource: 'BBBBBB',
            primaryAmount: 1,
            pecondarySource: 'BBBBBB',
            pecondaryAmount: 1,
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

      it('should delete a KycPersonal', () => {
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
