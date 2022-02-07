import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITranferDetailsApprovals, TranferDetailsApprovals } from '../tranfer-details-approvals.model';
import { TranferDetailsApprovalsService } from '../service/tranfer-details-approvals.service';
import { ITransferDetails } from 'app/entities/transfer-details/transfer-details.model';
import { TransferDetailsService } from 'app/entities/transfer-details/service/transfer-details.service';

@Component({
  selector: 'jhi-tranfer-details-approvals-update',
  templateUrl: './tranfer-details-approvals-update.component.html',
})
export class TranferDetailsApprovalsUpdateComponent implements OnInit {
  isSaving = false;

  transferDetailsSharedCollection: ITransferDetails[] = [];

  editForm = this.fb.group({
    id: [],
    approvalDate: [],
    transferId: [],
    qtyRequested: [],
    qtyApproved: [],
    comment: [],
    freeField1: [],
    freeField2: [],
    lastModified: [],
    lastModifiedBy: [],
    isDeleted: [],
    isActive: [],
    transferDetails: [],
  });

  constructor(
    protected tranferDetailsApprovalsService: TranferDetailsApprovalsService,
    protected transferDetailsService: TransferDetailsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tranferDetailsApprovals }) => {
      if (tranferDetailsApprovals.id === undefined) {
        const today = dayjs().startOf('day');
        tranferDetailsApprovals.approvalDate = today;
        tranferDetailsApprovals.lastModified = today;
      }

      this.updateForm(tranferDetailsApprovals);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tranferDetailsApprovals = this.createFromForm();
    if (tranferDetailsApprovals.id !== undefined) {
      this.subscribeToSaveResponse(this.tranferDetailsApprovalsService.update(tranferDetailsApprovals));
    } else {
      this.subscribeToSaveResponse(this.tranferDetailsApprovalsService.create(tranferDetailsApprovals));
    }
  }

  trackTransferDetailsById(index: number, item: ITransferDetails): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITranferDetailsApprovals>>): void {
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

  protected updateForm(tranferDetailsApprovals: ITranferDetailsApprovals): void {
    this.editForm.patchValue({
      id: tranferDetailsApprovals.id,
      approvalDate: tranferDetailsApprovals.approvalDate ? tranferDetailsApprovals.approvalDate.format(DATE_TIME_FORMAT) : null,
      transferId: tranferDetailsApprovals.transferId,
      qtyRequested: tranferDetailsApprovals.qtyRequested,
      qtyApproved: tranferDetailsApprovals.qtyApproved,
      comment: tranferDetailsApprovals.comment,
      freeField1: tranferDetailsApprovals.freeField1,
      freeField2: tranferDetailsApprovals.freeField2,
      lastModified: tranferDetailsApprovals.lastModified ? tranferDetailsApprovals.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: tranferDetailsApprovals.lastModifiedBy,
      isDeleted: tranferDetailsApprovals.isDeleted,
      isActive: tranferDetailsApprovals.isActive,
      transferDetails: tranferDetailsApprovals.transferDetails,
    });

    this.transferDetailsSharedCollection = this.transferDetailsService.addTransferDetailsToCollectionIfMissing(
      this.transferDetailsSharedCollection,
      tranferDetailsApprovals.transferDetails
    );
  }

  protected loadRelationshipsOptions(): void {
    this.transferDetailsService
      .query()
      .pipe(map((res: HttpResponse<ITransferDetails[]>) => res.body ?? []))
      .pipe(
        map((transferDetails: ITransferDetails[]) =>
          this.transferDetailsService.addTransferDetailsToCollectionIfMissing(transferDetails, this.editForm.get('transferDetails')!.value)
        )
      )
      .subscribe((transferDetails: ITransferDetails[]) => (this.transferDetailsSharedCollection = transferDetails));
  }

  protected createFromForm(): ITranferDetailsApprovals {
    return {
      ...new TranferDetailsApprovals(),
      id: this.editForm.get(['id'])!.value,
      approvalDate: this.editForm.get(['approvalDate'])!.value
        ? dayjs(this.editForm.get(['approvalDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      transferId: this.editForm.get(['transferId'])!.value,
      qtyRequested: this.editForm.get(['qtyRequested'])!.value,
      qtyApproved: this.editForm.get(['qtyApproved'])!.value,
      comment: this.editForm.get(['comment'])!.value,
      freeField1: this.editForm.get(['freeField1'])!.value,
      freeField2: this.editForm.get(['freeField2'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      transferDetails: this.editForm.get(['transferDetails'])!.value,
    };
  }
}
