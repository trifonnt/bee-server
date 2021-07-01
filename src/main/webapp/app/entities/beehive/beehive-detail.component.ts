import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBeehive } from 'app/shared/model/beehive.model';

@Component({
  selector: 'jhi-beehive-detail',
  templateUrl: './beehive-detail.component.html',
})
export class BeehiveDetailComponent implements OnInit {
  beehive: IBeehive | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ beehive }) => (this.beehive = beehive));
  }

  previousState(): void {
    window.history.back();
  }
}
