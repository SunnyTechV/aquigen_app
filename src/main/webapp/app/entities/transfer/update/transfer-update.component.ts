import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITransfer, Transfer } from '../transfer.model';
import { TransferService } from '../service/transfer.service';
import { IRmInventory } from 'app/entities/rm-inventory/rm-inventory.model';
import { RmInventoryService } from 'app/entities/rm-inventory/service/rm-inventory.service';
import { Status } from 'app/entities/enumerations/status.model';

@Component({
  selector: 'jhi-transfer-update',
  templateUrl: './transfer-update.component.html',
})
export class TransferUpdateComponent implements OnInit {
  isSaving = false;
  statusValues = Object.keys(Status);

  rmInventoriesSharedCollection: IRmInventory[] = [];

  editForm = this.fb.group({
    id: [],
    tranferDate: [],
    comment: [],
    isApproved: [],
    isRecieved: [],
    status: [],
    freeField1: [],
    freeField2: [],
    lastModified: [],
    lastModifiedBy: [],
    rmInventory: [],
  });

  constructor(
    protected transferService: TransferService,
    protected rmInventoryService: RmInventoryService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transfer }) => {
      if (transfer.id === undefined) {
        const today = dayjs().startOf('day');
        transfer.tranferDate = today;
        transfer.lastModified = today;
      }

      this.updateForm(transfer);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transfer = this.createFromForm();
    if (transfer.id !== undefined) {
      this.subscribeToSaveResponse(this.transferService.update(transfer));
    } else {
      this.subscribeToSaveResponse(this.transferService.create(transfer));
    }
  }

  trackRmInventoryById(index: number, item: IRmInventory): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransfer>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(transfer: ITransfer): void {
    this.editForm.patchValue({
      id: transfer.id,
      tranferDate: transfer.tranferDate ? transfer.tranferDate.format(DATE_TIME_FORMAT) : null,
      comment: transfer.comment,
      isApproved: transfer.isApproved,
      isRecieved: transfer.isRecieved,
      status: transfer.status,
      freeField1: transfer.freeField1,
      freeField2: transfer.freeField2,
      lastModified: transfer.lastModified ? transfer.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: transfer.lastModifiedBy,
      rmInventory: transfer.rmInventory,
    });

    this.rmInventoriesSharedCollection = this.rmInventoryService.addRmInventoryToCollectionIfMissing(
      this.rmInventoriesSharedCollection,
      transfer.rmInventory
    );
  }

  protected loadRelationshipsOptions(): void {
    this.rmInventoryService
      .query()
      .pipe(map((res: HttpResponse<IRmInventory[]>) => res.body ?? []))
      .pipe(
        map((rmInventories: IRmInventory[]) =>
          this.rmInventoryService.addRmInventoryToCollectionIfMissing(rmInventories, this.editForm.get('rmInventory')!.value)
        )
      )
      .subscribe((rmInventories: IRmInventory[]) => (this.rmInventoriesSharedCollection = rmInventories));
  }

  protected createFromForm(): ITransfer {
    return {
      ...new Transfer(),
      id: this.editForm.get(['id'])!.value,
      tranferDate: this.editForm.get(['tranferDate'])!.value
        ? dayjs(this.editForm.get(['tranferDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      comment: this.editForm.get(['comment'])!.value,
      isApproved: this.editForm.get(['isApproved'])!.value,
      isRecieved: this.editForm.get(['isRecieved'])!.value,
      status: this.editForm.get(['status'])!.value,
      freeField1: this.editForm.get(['freeField1'])!.value,
      freeField2: this.editForm.get(['freeField2'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      rmInventory: this.editForm.get(['rmInventory'])!.value,
    };
  }
}
