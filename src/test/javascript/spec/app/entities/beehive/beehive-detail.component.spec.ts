import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BeeServerTestModule } from '../../../test.module';
import { BeehiveDetailComponent } from 'app/entities/beehive/beehive-detail.component';
import { Beehive } from 'app/shared/model/beehive.model';

describe('Component Tests', () => {
  describe('Beehive Management Detail Component', () => {
    let comp: BeehiveDetailComponent;
    let fixture: ComponentFixture<BeehiveDetailComponent>;
    const route = ({ data: of({ beehive: new Beehive(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BeeServerTestModule],
        declarations: [BeehiveDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BeehiveDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BeehiveDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load beehive on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.beehive).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
