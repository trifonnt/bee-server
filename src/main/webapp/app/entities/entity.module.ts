import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'apiary',
        loadChildren: () => import('./apiary/apiary.module').then(m => m.BeeServerApiaryModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class BeeServerEntityModule {}
