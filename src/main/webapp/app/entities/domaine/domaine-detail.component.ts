import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDomaine } from 'app/shared/model/domaine.model';

@Component({
  selector: 'jhi-domaine-detail',
  templateUrl: './domaine-detail.component.html',
})
export class DomaineDetailComponent implements OnInit {
  domaine: IDomaine | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ domaine }) => (this.domaine = domaine));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
