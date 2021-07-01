import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BeeServerSharedModule } from 'app/shared/shared.module';
import { BeehiveComponent } from './beehive.component';
import { BeehiveDetailComponent } from './beehive-detail.component';
import { BeehiveUpdateComponent } from './beehive-update.component';
import { BeehiveDeleteDialogComponent } from './beehive-delete-dialog.component';
import { beehiveRoute } from './beehive.route';

@NgModule({
  imports: [BeeServerSharedModule, RouterModule.forChild(beehiveRoute)],
  declarations: [BeehiveComponent, BeehiveDetailComponent, BeehiveUpdateComponent, BeehiveDeleteDialogComponent],
  entryComponents: [BeehiveDeleteDialogComponent],
})
export class BeeServerBeehiveModule {}
