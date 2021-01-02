import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PeerToPeerService } from 'app/entities/peer-to-peer/peer-to-peer.service';
import { IPeerToPeer, PeerToPeer } from 'app/shared/model/peer-to-peer.model';

describe('Service Tests', () => {
  describe('PeerToPeer Service', () => {
    let injector: TestBed;
    let service: PeerToPeerService;
    let httpMock: HttpTestingController;
    let elemDefault: IPeerToPeer;
    let expectedResult: IPeerToPeer | IPeerToPeer[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PeerToPeerService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new PeerToPeer(0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PeerToPeer', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PeerToPeer()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PeerToPeer', () => {
        const returnedFromService = Object.assign(
          {
            amount: 1,
            currency: 'BBBBBB',
            beneficiaryRelationship: 'BBBBBB',
            purposeOfTransfer: 'BBBBBB',
            transactionType: 'BBBBBB',
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

      it('should return a list of PeerToPeer', () => {
        const returnedFromService = Object.assign(
          {
            amount: 1,
            currency: 'BBBBBB',
            beneficiaryRelationship: 'BBBBBB',
            purposeOfTransfer: 'BBBBBB',
            transactionType: 'BBBBBB',
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

      it('should delete a PeerToPeer', () => {
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
