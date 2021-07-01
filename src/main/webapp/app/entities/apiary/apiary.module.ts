import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BeeServerSharedModule } from 'app/shared/shared.module';
import { ApiaryComponent } from './apiary.component';
import { ApiaryDetailComponent } from './apiary-detail.component';
import { ApiaryUpdateComponent } from './apiary-update.component';
import { ApiaryDeleteDialogComponent } from './apiary-delete-dialog.component';
import { apiaryRoute } from './apiary.route';

@NgModule({
  imports: [BeeServerSharedModule, RouterModule.forChild(apiaryRoute)],
  declarations: [ApiaryComponent, ApiaryDetailComponent, ApiaryUpdateComponent, ApiaryDeleteDialogComponent],
  entryComponents: [ApiaryDeleteDialogComponent],
})
export class BeeServerApiaryModule {}
