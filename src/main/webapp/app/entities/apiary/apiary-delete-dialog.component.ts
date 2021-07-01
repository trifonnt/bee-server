import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApiary } from 'app/shared/model/apiary.model';
import { ApiaryService } from './apiary.service';

@Component({
  templateUrl: './apiary-delete-dialog.component.html',
})
export class ApiaryDeleteDialogComponent {
  apiary?: IApiary;

  constructor(protected apiaryService: ApiaryService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.apiaryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('apiaryListModification');
      this.activeModal.close();
    });
  }
}
