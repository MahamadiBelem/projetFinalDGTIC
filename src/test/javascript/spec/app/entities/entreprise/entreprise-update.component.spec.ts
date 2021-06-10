import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ProjetDgticTestModule } from '../../../test.module';
import { EntrepriseUpdateComponent } from 'app/entities/entreprise/entreprise-update.component';
import { EntrepriseService } from 'app/entities/entreprise/entreprise.service';
import { Entreprise } from 'app/shared/model/entreprise.model';

describe('Component Tests', () => {
  describe('Entreprise Management Update Component', () => {
    let comp: EntrepriseUpdateComponent;
    let fixture: ComponentFixture<EntrepriseUpdateComponent>;
    let service: EntrepriseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProjetDgticTestModule],
        declarations: [EntrepriseUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EntrepriseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EntrepriseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EntrepriseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Entreprise(123);
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
        const entity = new Entreprise();
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
