import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BeeServerTestModule } from '../../../test.module';
import { ApiaryDetailComponent } from 'app/entities/apiary/apiary-detail.component';
import { Apiary } from 'app/shared/model/apiary.model';

describe('Component Tests', () => {
  describe('Apiary Management Detail Component', () => {
    let comp: ApiaryDetailComponent;
    let fixture: ComponentFixture<ApiaryDetailComponent>;
    const route = ({ data: of({ apiary: new Apiary(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BeeServerTestModule],
        declarations: [ApiaryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ApiaryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApiaryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load apiary on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.apiary).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
