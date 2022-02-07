import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RawMaterialMasterComponent } from './list/raw-material-master.component';
import { RawMaterialMasterDetailComponent } from './detail/raw-material-master-detail.component';
import { RawMaterialMasterUpdateComponent } from './update/raw-material-master-update.component';
import { RawMaterialMasterDeleteDialogComponent } from './delete/raw-material-master-delete-dialog.component';
import { RawMaterialMasterRoutingModule } from './route/raw-material-master-routing.module';

@NgModule({
  imports: [SharedModule, RawMaterialMasterRoutingModule],
  declarations: [
    RawMaterialMasterComponent,
    RawMaterialMasterDetailComponent,
    RawMaterialMasterUpdateComponent,
    RawMaterialMasterDeleteDialogComponent,
  ],
  entryComponents: [RawMaterialMasterDeleteDialogComponent],
})
export class RawMaterialMasterModule {}
