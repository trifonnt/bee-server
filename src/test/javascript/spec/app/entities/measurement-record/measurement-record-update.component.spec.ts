import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BeeServerTestModule } from '../../../test.module';
import { MeasurementRecordUpdateComponent } from 'app/entities/measurement-record/measurement-record-update.component';
import { MeasurementRecordService } from 'app/entities/measurement-record/measurement-record.service';
import { MeasurementRecord } from 'app/shared/model/measurement-record.model';

describe('Component Tests', () => {
  describe('MeasurementRecord Management Update Component', () => {
    let comp: MeasurementRecordUpdateComponent;
    let fixture: ComponentFixture<MeasurementRecordUpdateComponent>;
    let service: MeasurementRecordService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BeeServerTestModule],
        declarations: [MeasurementRecordUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MeasurementRecordUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MeasurementRecordUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MeasurementRecordService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MeasurementRecord(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new MeasurementRecord();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
