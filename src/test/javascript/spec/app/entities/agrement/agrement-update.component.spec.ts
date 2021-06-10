import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ProjetDgticTestModule } from '../../../test.module';
import { AgrementUpdateComponent } from 'app/entities/agrement/agrement-update.component';
import { AgrementService } from 'app/entities/agrement/agrement.service';
import { Agrement } from 'app/shared/model/agrement.model';

describe('Component Tests', () => {
  describe('Agrement Management Update Component', () => {
    let comp: AgrementUpdateComponent;
    let fixture: ComponentFixture<AgrementUpdateComponent>;
    let service: AgrementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProjetDgticTestModule],
        declarations: [AgrementUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AgrementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AgrementUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgrementService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Agrement(123);
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
        const entity = new Agrement();
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
