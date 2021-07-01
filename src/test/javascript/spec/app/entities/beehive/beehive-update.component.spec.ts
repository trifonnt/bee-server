import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BeeServerTestModule } from '../../../test.module';
import { BeehiveUpdateComponent } from 'app/entities/beehive/beehive-update.component';
import { BeehiveService } from 'app/entities/beehive/beehive.service';
import { Beehive } from 'app/shared/model/beehive.model';

describe('Component Tests', () => {
  describe('Beehive Management Update Component', () => {
    let comp: BeehiveUpdateComponent;
    let fixture: ComponentFixture<BeehiveUpdateComponent>;
    let service: BeehiveService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BeeServerTestModule],
        declarations: [BeehiveUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BeehiveUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BeehiveUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BeehiveService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Beehive(123);
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
        const entity = new Beehive();
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
