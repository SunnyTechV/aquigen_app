import dayjs from 'dayjs/esm';
import { ITransferDetails } from 'app/entities/transfer-details/transfer-details.model';

export interface ITranferRecieved {
  id?: number;
  transferDate?: dayjs.Dayjs | null;
  transferId?: number | null;
  qtyTransfered?: number | null;
  qtyReceived?: number | null;
  comment?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  isDeleted?: boolean | null;
  isActive?: boolean | null;
  transferDetails?: ITransferDetails | null;
}

export class TranferRecieved implements ITranferRecieved {
  constructor(
    public id?: number,
    public transferDate?: dayjs.Dayjs | null,
    public transferId?: number | null,
    public qtyTransfered?: number | null,
    public qtyReceived?: number | null,
    public comment?: string | null,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public isDeleted?: boolean | null,
    public isActive?: boolean | null,
    public transferDetails?: ITransferDetails | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
    this.isActive = this.isActive ?? false;
  }
}

export function getTranferRecievedIdentifier(tranferRecieved: ITranferRecieved): number | undefined {
  return tranferRecieved.id;
}
