import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBeehive, Beehive } from 'app/shared/model/beehive.model';
import { BeehiveService } from './beehive.service';
import { IApiary } from 'app/shared/model/apiary.model';
import { ApiaryService } from 'app/entities/apiary/apiary.service';

@Component({
  selector: 'jhi-beehive-update',
  templateUrl: './beehive-update.component.html',
})
export class BeehiveUpdateComponent implements OnInit {
  isSaving = false;
  apiaries: IApiary[] = [];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    name: [null, [Validators.required]],
    active: [],
    description: [],
    yearCreated: [],
    monthCreated: [],
    apiaryId: [null, Validators.required],
  });

  constructor(
    protected beehiveService: BeehiveService,
    protected apiaryService: ApiaryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ beehive }) => {
      this.updateForm(beehive);

      this.apiaryService.query().subscribe((res: HttpResponse<IApiary[]>) => (this.apiaries = res.body || []));
    });
  }

  updateForm(beehive: IBeehive): void {
    this.editForm.patchValue({
      id: beehive.id,
      code: beehive.code,
      name: beehive.name,
      active: beehive.active,
      description: beehive.description,
      yearCreated: beehive.yearCreated,
      monthCreated: beehive.monthCreated,
      apiaryId: beehive.apiaryId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const beehive = this.createFromForm();
    if (beehive.id !== undefined) {
      this.subscribeToSaveResponse(this.beehiveService.update(beehive));
    } else {
      this.subscribeToSaveResponse(this.beehiveService.create(beehive));
    }
  }

  private createFromForm(): IBeehive {
    return {
      ...new Beehive(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
      active: this.editForm.get(['active'])!.value,
      description: this.editForm.get(['description'])!.value,
      yearCreated: this.editForm.get(['yearCreated'])!.value,
      monthCreated: this.editForm.get(['monthCreated'])!.value,
      apiaryId: this.editForm.get(['apiaryId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBeehive>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IApiary): any {
    return item.id;
  }
}
