import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProjetDgticTestModule } from '../../../test.module';
import { ArreteDetailComponent } from 'app/entities/arrete/arrete-detail.component';
import { Arrete } from 'app/shared/model/arrete.model';

describe('Component Tests', () => {
  describe('Arrete Management Detail Component', () => {
    let comp: ArreteDetailComponent;
    let fixture: ComponentFixture<ArreteDetailComponent>;
    const route = ({ data: of({ arrete: new Arrete(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProjetDgticTestModule],
        declarations: [ArreteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ArreteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ArreteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load arrete on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.arrete).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
