import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RmInventoryComponent } from './list/rm-inventory.component';
import { RmInventoryDetailComponent } from './detail/rm-inventory-detail.component';
import { RmInventoryUpdateComponent } from './update/rm-inventory-update.component';
import { RmInventoryDeleteDialogComponent } from './delete/rm-inventory-delete-dialog.component';
import { RmInventoryRoutingModule } from './route/rm-inventory-routing.module';

@NgModule({
  imports: [SharedModule, RmInventoryRoutingModule],
  declarations: [RmInventoryComponent, RmInventoryDetailComponent, RmInventoryUpdateComponent, RmInventoryDeleteDialogComponent],
  entryComponents: [RmInventoryDeleteDialogComponent],
})
export class RmInventoryModule {}
