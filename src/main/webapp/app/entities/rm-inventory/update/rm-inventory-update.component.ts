import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IRmInventory, RmInventory } from '../rm-inventory.model';
import { RmInventoryService } from '../service/rm-inventory.service';

@Component({
  selector: 'jhi-rm-inventory-update',
  templateUrl: './rm-inventory-update.component.html',
})
export class RmInventoryUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    inwardDate: [],
    inwardQty: [],
    outwardQty: [],
    outwardDate: [],
    totalQuanity: [],
    pricePerUnit: [],
    lotNo: [],
    expiryDate: [],
    freeField1: [],
    freeField2: [],
    freeField3: [],
    freeField4: [],
    lastModified: [],
    lastModifiedBy: [],
    isDeleted: [],
    isActive: [],
  });

  constructor(protected rmInventoryService: RmInventoryService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rmInventory }) => {
      if (rmInventory.id === undefined) {
        const today = dayjs().startOf('day');
        rmInventory.inwardDate = today;
        rmInventory.outwardDate = today;
        rmInventory.expiryDate = today;
        rmInventory.lastModified = today;
      }

      this.updateForm(rmInventory);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rmInventory = this.createFromForm();
    if (rmInventory.id !== undefined) {
      this.subscribeToSaveResponse(this.rmInventoryService.update(rmInventory));
    } else {
      this.subscribeToSaveResponse(this.rmInventoryService.create(rmInventory));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRmInventory>>): void {
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

  protected updateForm(rmInventory: IRmInventory): void {
    this.editForm.patchValue({
      id: rmInventory.id,
      inwardDate: rmInventory.inwardDate ? rmInventory.inwardDate.format(DATE_TIME_FORMAT) : null,
      inwardQty: rmInventory.inwardQty,
      outwardQty: rmInventory.outwardQty,
      outwardDate: rmInventory.outwardDate ? rmInventory.outwardDate.format(DATE_TIME_FORMAT) : null,
      totalQuanity: rmInventory.totalQuanity,
      pricePerUnit: rmInventory.pricePerUnit,
      lotNo: rmInventory.lotNo,
      expiryDate: rmInventory.expiryDate ? rmInventory.expiryDate.format(DATE_TIME_FORMAT) : null,
      freeField1: rmInventory.freeField1,
      freeField2: rmInventory.freeField2,
      freeField3: rmInventory.freeField3,
      freeField4: rmInventory.freeField4,
      lastModified: rmInventory.lastModified ? rmInventory.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: rmInventory.lastModifiedBy,
      isDeleted: rmInventory.isDeleted,
      isActive: rmInventory.isActive,
    });
  }

  protected createFromForm(): IRmInventory {
    return {
      ...new RmInventory(),
      id: this.editForm.get(['id'])!.value,
      inwardDate: this.editForm.get(['inwardDate'])!.value ? dayjs(this.editForm.get(['inwardDate'])!.value, DATE_TIME_FORMAT) : undefined,
      inwardQty: this.editForm.get(['inwardQty'])!.value,
      outwardQty: this.editForm.get(['outwardQty'])!.value,
      outwardDate: this.editForm.get(['outwardDate'])!.value
        ? dayjs(this.editForm.get(['outwardDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      totalQuanity: this.editForm.get(['totalQuanity'])!.value,
      pricePerUnit: this.editForm.get(['pricePerUnit'])!.value,
      lotNo: this.editForm.get(['lotNo'])!.value,
      expiryDate: this.editForm.get(['expiryDate'])!.value ? dayjs(this.editForm.get(['expiryDate'])!.value, DATE_TIME_FORMAT) : undefined,
      freeField1: this.editForm.get(['freeField1'])!.value,
      freeField2: this.editForm.get(['freeField2'])!.value,
      freeField3: this.editForm.get(['freeField3'])!.value,
      freeField4: this.editForm.get(['freeField4'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
    };
  }
}
