import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBeehive } from 'app/shared/model/beehive.model';
import { BeehiveService } from './beehive.service';

@Component({
  templateUrl: './beehive-delete-dialog.component.html',
})
export class BeehiveDeleteDialogComponent {
  beehive?: IBeehive;

  constructor(protected beehiveService: BeehiveService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.beehiveService.delete(id).subscribe(() => {
      this.eventManager.broadcast('beehiveListModification');
      this.activeModal.close();
    });
  }
}
