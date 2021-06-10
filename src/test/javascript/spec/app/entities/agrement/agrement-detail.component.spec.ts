import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProjetDgticTestModule } from '../../../test.module';
import { AgrementDetailComponent } from 'app/entities/agrement/agrement-detail.component';
import { Agrement } from 'app/shared/model/agrement.model';

describe('Component Tests', () => {
  describe('Agrement Management Detail Component', () => {
    let comp: AgrementDetailComponent;
    let fixture: ComponentFixture<AgrementDetailComponent>;
    const route = ({ data: of({ agrement: new Agrement(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProjetDgticTestModule],
        declarations: [AgrementDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AgrementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AgrementDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load agrement on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.agrement).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
