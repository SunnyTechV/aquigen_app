import dayjs from 'dayjs/esm';
import { ITranferDetailsApprovals } from 'app/entities/tranfer-details-approvals/tranfer-details-approvals.model';
import { ITranferRecieved } from 'app/entities/tranfer-recieved/tranfer-recieved.model';
import { ITransfer } from 'app/entities/transfer/transfer.model';

export interface ITransferDetails {
  id?: number;
  approvalDate?: dayjs.Dayjs | null;
  transferId?: number | null;
  qty?: number | null;
  comment?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  isDeleted?: boolean | null;
  isActive?: boolean | null;
  tranferDetailsApprovals?: ITranferDetailsApprovals[] | null;
  tranferRecieveds?: ITranferRecieved[] | null;
  transfer?: ITransfer | null;
}

export class TransferDetails implements ITransferDetails {
  constructor(
    public id?: number,
    public approvalDate?: dayjs.Dayjs | null,
    public transferId?: number | null,
    public qty?: number | null,
    public comment?: string | null,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public isDeleted?: boolean | null,
    public isActive?: boolean | null,
    public tranferDetailsApprovals?: ITranferDetailsApprovals[] | null,
    public tranferRecieveds?: ITranferRecieved[] | null,
    public transfer?: ITransfer | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
    this.isActive = this.isActive ?? false;
  }
}

export function getTransferDetailsIdentifier(transferDetails: ITransferDetails): number | undefined {
  return transferDetails.id;
}
