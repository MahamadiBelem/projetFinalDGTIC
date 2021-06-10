import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ArreteService } from 'app/entities/arrete/arrete.service';
import { IArrete, Arrete } from 'app/shared/model/arrete.model';

describe('Service Tests', () => {
  describe('Arrete Service', () => {
    let injector: TestBed;
    let service: ArreteService;
    let httpMock: HttpTestingController;
    let elemDefault: IArrete;
    let expectedResult: IArrete | IArrete[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ArreteService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Arrete(0, 'AAAAAAA', 'AAAAAAA', currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateSignature: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Arrete', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateSignature: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateSignature: currentDate,
          },
          returnedFromService
        );

        service.create(new Arrete()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Arrete', () => {
        const returnedFromService = Object.assign(
          {
            intituleArrete: 'BBBBBB',
            numeroArrete: 'BBBBBB',
            dateSignature: currentDate.format(DATE_FORMAT),
            nombreAgrement: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateSignature: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Arrete', () => {
        const returnedFromService = Object.assign(
          {
            intituleArrete: 'BBBBBB',
            numeroArrete: 'BBBBBB',
            dateSignature: currentDate.format(DATE_FORMAT),
            nombreAgrement: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateSignature: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Arrete', () => {
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
