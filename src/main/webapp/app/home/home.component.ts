import { Component, OnInit, OnDestroy, Output, EventEmitter } from '@angular/core';
import { combineLatest, Observable, Subscription } from 'rxjs';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { EntrepriseService } from 'app/entities/entreprise/entreprise.service';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Entreprise, IEntreprise } from 'app/shared/model/entreprise.model';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
//import { createRequestOption } from 'app/shared/util/request-util';

//import { SERVER_API_URL } from 'app/app.constants';
import { JhiDataUtils, JhiEventManager } from 'ng-jhipster';
//import { NgbModal } from '@ng-bootstrap/ng-bootstrap/modal/modal.module';

//type EntityResponseType = HttpResponse<IEntreprise>;
//type EntityArrayResponseType = HttpResponse<IEntreprise[]>;

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss'],
  //providers: [EntrepriseService]
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  authSubscription?: Subscription;
  isNavbarCollapsed = true;
  // term!: string;

  //@Output() dataLoaded: EventEmitter<any> = new EventEmitter<any>();

  filter = '';
  orderProp = 'name';
  reverse = false;

  entreprises?: Entreprise[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  // term!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  //data: any[] = [];

  //public resourceUrl = SERVER_API_URL + 'api/entreprises'; protected modalService: NgbModal

  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private entrepriseService: EntrepriseService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.entrepriseService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IEntreprise[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInEntreprises();
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));

    // this.entSubscription = this.entrepriseService.loadPage().subscribe(this.entreprises => (this.entreprises = entreprises);
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginModalService.open();
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }
  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
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

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IEntreprise[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/entreprise'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.entreprises = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }

  trackId(index: number, item: IEntreprise): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInEntreprises(): void {
    this.eventSubscriber = this.eventManager.subscribe('entrepriseListModification', () => this.loadPage());
  }

  /*
  create(entreprise: IEntreprise): Observable<EntityResponseType> {
    return this.http.post<IEntreprise>(this.resourceUrl, entreprise, { observe: 'response' });
  }

  update(entreprise: IEntreprise): Observable<EntityResponseType> {
    return this.http.put<IEntreprise>(this.resourceUrl, entreprise, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEntreprise>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEntreprise[]>(this.resourceUrl, { params: options, observe: 'response' });
  }*/
}
