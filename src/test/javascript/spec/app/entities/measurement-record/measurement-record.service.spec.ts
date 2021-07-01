import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { MeasurementRecordService } from 'app/entities/measurement-record/measurement-record.service';
import { IMeasurementRecord, MeasurementRecord } from 'app/shared/model/measurement-record.model';

describe('Service Tests', () => {
  describe('MeasurementRecord Service', () => {
    let injector: TestBed;
    let service: MeasurementRecordService;
    let httpMock: HttpTestingController;
    let elemDefault: IMeasurementRecord;
    let expectedResult: IMeasurementRecord | IMeasurementRecord[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MeasurementRecordService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new MeasurementRecord(0, currentDate, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            recordedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a MeasurementRecord', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            recordedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            recordedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new MeasurementRecord()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MeasurementRecord', () => {
        const returnedFromService = Object.assign(
          {
            recordedAt: currentDate.format(DATE_TIME_FORMAT),
            inboundCount: 1,
            outboundCount: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            recordedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MeasurementRecord', () => {
        const returnedFromService = Object.assign(
          {
            recordedAt: currentDate.format(DATE_TIME_FORMAT),
            inboundCount: 1,
            outboundCount: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            recordedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a MeasurementRecord', () => {
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
