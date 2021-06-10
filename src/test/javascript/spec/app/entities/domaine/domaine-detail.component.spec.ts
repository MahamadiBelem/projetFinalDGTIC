import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ProjetDgticTestModule } from '../../../test.module';
import { DomaineDetailComponent } from 'app/entities/domaine/domaine-detail.component';
import { Domaine } from 'app/shared/model/domaine.model';

describe('Component Tests', () => {
  describe('Domaine Management Detail Component', () => {
    let comp: DomaineDetailComponent;
    let fixture: ComponentFixture<DomaineDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ domaine: new Domaine(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProjetDgticTestModule],
        declarations: [DomaineDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DomaineDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DomaineDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load domaine on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.domaine).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
