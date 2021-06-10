import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ProjetDgticTestModule } from '../../../test.module';
import { CategorieDetailComponent } from 'app/entities/categorie/categorie-detail.component';
import { Categorie } from 'app/shared/model/categorie.model';

describe('Component Tests', () => {
  describe('Categorie Management Detail Component', () => {
    let comp: CategorieDetailComponent;
    let fixture: ComponentFixture<CategorieDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ categorie: new Categorie(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProjetDgticTestModule],
        declarations: [CategorieDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CategorieDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategorieDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load categorie on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categorie).toEqual(jasmine.objectContaining({ id: 123 }));
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
