import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BeeServerTestModule } from '../../../test.module';
import { DeviceUpdateComponent } from 'app/entities/device/device-update.component';
import { DeviceService } from 'app/entities/device/device.service';
import { Device } from 'app/shared/model/device.model';

describe('Component Tests', () => {
  describe('Device Management Update Component', () => {
    let comp: DeviceUpdateComponent;
    let fixture: ComponentFixture<DeviceUpdateComponent>;
    let service: DeviceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BeeServerTestModule],
        declarations: [DeviceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DeviceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeviceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeviceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Device(123);
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
        const entity = new Device();
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
