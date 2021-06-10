import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAgrement } from 'app/shared/model/agrement.model';

@Component({
  selector: 'jhi-agrement-detail',
  templateUrl: './agrement-detail.component.html',
})
export class AgrementDetailComponent implements OnInit {
  agrement: IAgrement | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agrement }) => (this.agrement = agrement));
  }

  previousState(): void {
    window.history.back();
  }
}
