import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest, Observable } from 'rxjs';
import { JhiEventManager, JhiDataUtils, JhiEventWithContent, JhiFileLoadError } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { Domaine, IDomaine } from 'app/shared/model/domaine.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DomaineService } from './domaine.service';
import { DomaineDeleteDialogComponent } from './domaine-delete-dialog.component';
import { FormBuilder, Validators } from '@angular/forms';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-domaine',
  templateUrl: './domaine.component.html',
})
export class DomaineComponent implements OnInit, OnDestroy {
  domaines?: IDomaine[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  //
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    codeDomaine: [null, [Validators.required]],
    libelle: [null, [Validators.required]],
  });

  constructor(
    protected domaineService: DomaineService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,

    //
    // protected dataUtils: JhiDataUtils,
    // protected eventManager: JhiEventManager,
    // protected domaineService: DomaineService,
    //protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.domaineService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IDomaine[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInDomaines();
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    }).subscribe();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDomaine): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInDomaines(): void {
    this.eventSubscriber = this.eventManager.subscribe('domaineListModification', () => this.loadPage());
  }

  delete(domaine: IDomaine): void {
    const modalRef = this.modalService.open(DomaineDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.domaine = domaine;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IDomaine[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/domaine'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.domaines = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }

  //

  updateForm(domaine: IDomaine): void {
    this.editForm.patchValue({
      id: domaine.id,
      codeDomaine: domaine.codeDomaine,
      libelle: domaine.libelle,
    });
  }

  /* byteSize(base64String: string): string {
     return this.dataUtils.byteSize(base64String);
   }
 
   openFile(contentType: string, base64String: string): void {
     this.dataUtils.openFile(contentType, base64String);
   }*/

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('projetDgticApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  /* save(): void {
     this.isSaving = true;
     const domaine = this.createFromForm();
     if (domaine.id !== undefined) {
       this.subscribeToSaveResponse(this.domaineService.update(domaine));
     } else {
       this.subscribeToSaveResponse(this.domaineService.create(domaine));
     }
   }*/

  //

  save(): void {
    const domaine = this.createFromForm();
    this.isSaving = true;
    this.subscribeToSaveResponse(this.domaineService.create(domaine));
    // this.activeModal.close();
    //this.NextState();
    // this.activeModal.close();
  }
  private createFromForm(): IDomaine {
    return {
      ...new Domaine(),
      id: this.editForm.get(['id'])!.value,
      codeDomaine: this.editForm.get(['codeDomaine'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDomaine>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;

    //this.eventManager.broadcast("succes de l'operation");
    // this.eventManager.
    alert('creation effectué avec succes');
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  /* public reLoad() {
     //this.router.navigate([this.router.url])
     this.router.navigate(['/domaine'])
   }*/
}
