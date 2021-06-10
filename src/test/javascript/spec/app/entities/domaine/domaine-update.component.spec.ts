import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ProjetDgticTestModule } from '../../../test.module';
import { DomaineUpdateComponent } from 'app/entities/domaine/domaine-update.component';
import { DomaineService } from 'app/entities/domaine/domaine.service';
import { Domaine } from 'app/shared/model/domaine.model';

describe('Component Tests', () => {
  describe('Domaine Management Update Component', () => {
    let comp: DomaineUpdateComponent;
    let fixture: ComponentFixture<DomaineUpdateComponent>;
    let service: DomaineService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProjetDgticTestModule],
        declarations: [DomaineUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DomaineUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DomaineUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DomaineService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Domaine(123);
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
        const entity = new Domaine();
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
