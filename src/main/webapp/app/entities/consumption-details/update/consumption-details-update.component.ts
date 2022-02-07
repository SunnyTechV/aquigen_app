import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IConsumptionDetails, ConsumptionDetails } from '../consumption-details.model';
import { ConsumptionDetailsService } from '../service/consumption-details.service';
import { IRmInventory } from 'app/entities/rm-inventory/rm-inventory.model';
import { RmInventoryService } from 'app/entities/rm-inventory/service/rm-inventory.service';

@Component({
  selector: 'jhi-consumption-details-update',
  templateUrl: './consumption-details-update.component.html',
})
export class ConsumptionDetailsUpdateComponent implements OnInit {
  isSaving = false;

  rmInventoriesSharedCollection: IRmInventory[] = [];

  editForm = this.fb.group({
    id: [],
    comsumptionDate: [],
    qtyConsumed: [],
    freeField1: [],
    freeField2: [],
    lastModified: [],
    lastModifiedBy: [],
    rmInventory: [],
  });

  constructor(
    protected consumptionDetailsService: ConsumptionDetailsService,
    protected rmInventoryService: RmInventoryService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ consumptionDetails }) => {
      if (consumptionDetails.id === undefined) {
        const today = dayjs().startOf('day');
        consumptionDetails.comsumptionDate = today;
        consumptionDetails.lastModified = today;
      }

      this.updateForm(consumptionDetails);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const consumptionDetails = this.createFromForm();
    if (consumptionDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.consumptionDetailsService.update(consumptionDetails));
    } else {
      this.subscribeToSaveResponse(this.consumptionDetailsService.create(consumptionDetails));
    }
  }

  trackRmInventoryById(index: number, item: IRmInventory): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConsumptionDetails>>): void {
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

  protected updateForm(consumptionDetails: IConsumptionDetails): void {
    this.editForm.patchValue({
      id: consumptionDetails.id,
      comsumptionDate: consumptionDetails.comsumptionDate ? consumptionDetails.comsumptionDate.format(DATE_TIME_FORMAT) : null,
      qtyConsumed: consumptionDetails.qtyConsumed,
      freeField1: consumptionDetails.freeField1,
      freeField2: consumptionDetails.freeField2,
      lastModified: consumptionDetails.lastModified ? consumptionDetails.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: consumptionDetails.lastModifiedBy,
      rmInventory: consumptionDetails.rmInventory,
    });

    this.rmInventoriesSharedCollection = this.rmInventoryService.addRmInventoryToCollectionIfMissing(
      this.rmInventoriesSharedCollection,
      consumptionDetails.rmInventory
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

  protected createFromForm(): IConsumptionDetails {
    return {
      ...new ConsumptionDetails(),
      id: this.editForm.get(['id'])!.value,
      comsumptionDate: this.editForm.get(['comsumptionDate'])!.value
        ? dayjs(this.editForm.get(['comsumptionDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      qtyConsumed: this.editForm.get(['qtyConsumed'])!.value,
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
