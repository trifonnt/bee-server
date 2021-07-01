import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BeeServerTestModule } from '../../../test.module';
import { MeasurementRecordDetailComponent } from 'app/entities/measurement-record/measurement-record-detail.component';
import { MeasurementRecord } from 'app/shared/model/measurement-record.model';

describe('Component Tests', () => {
  describe('MeasurementRecord Management Detail Component', () => {
    let comp: MeasurementRecordDetailComponent;
    let fixture: ComponentFixture<MeasurementRecordDetailComponent>;
    const route = ({ data: of({ measurementRecord: new MeasurementRecord(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BeeServerTestModule],
        declarations: [MeasurementRecordDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MeasurementRecordDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MeasurementRecordDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load measurementRecord on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.measurementRecord).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
