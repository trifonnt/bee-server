import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IApiary, Apiary } from 'app/shared/model/apiary.model';
import { ApiaryService } from './apiary.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-apiary-update',
  templateUrl: './apiary-update.component.html',
})
export class ApiaryUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    name: [null, [Validators.required]],
    active: [],
    description: [],
    address: [],
    ownerId: [null, Validators.required],
  });

  constructor(
    protected apiaryService: ApiaryService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ apiary }) => {
      this.updateForm(apiary);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(apiary: IApiary): void {
    this.editForm.patchValue({
      id: apiary.id,
      code: apiary.code,
      name: apiary.name,
      active: apiary.active,
      description: apiary.description,
      address: apiary.address,
      ownerId: apiary.ownerId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const apiary = this.createFromForm();
    if (apiary.id !== undefined) {
      this.subscribeToSaveResponse(this.apiaryService.update(apiary));
    } else {
      this.subscribeToSaveResponse(this.apiaryService.create(apiary));
    }
  }

  private createFromForm(): IApiary {
    return {
      ...new Apiary(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
      active: this.editForm.get(['active'])!.value,
      description: this.editForm.get(['description'])!.value,
      address: this.editForm.get(['address'])!.value,
      ownerId: this.editForm.get(['ownerId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApiary>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
