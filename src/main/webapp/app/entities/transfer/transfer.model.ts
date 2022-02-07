import dayjs from 'dayjs/esm';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { ITransferDetails } from 'app/entities/transfer-details/transfer-details.model';
import { IRmInventory } from 'app/entities/rm-inventory/rm-inventory.model';
import { Status } from 'app/entities/enumerations/status.model';

export interface ITransfer {
  id?: number;
  transferId?: number | null;
  tranferDate?: dayjs.Dayjs | null;
  comment?: string | null;
  isApproved?: boolean | null;
  isRecieved?: boolean | null;
  status?: Status | null;
  freeField1?: string | null;
  freeField2?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  securityUsers?: ISecurityUser[] | null;
  transferDetails?: ITransferDetails[] | null;
  rmInventory?: IRmInventory | null;
}

export class Transfer implements ITransfer {
  constructor(
    public id?: number,
    public transferId?: number | null,
    public tranferDate?: dayjs.Dayjs | null,
    public comment?: string | null,
    public isApproved?: boolean | null,
    public isRecieved?: boolean | null,
    public status?: Status | null,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public securityUsers?: ISecurityUser[] | null,
    public transferDetails?: ITransferDetails[] | null,
    public rmInventory?: IRmInventory | null
  ) {
    this.isApproved = this.isApproved ?? false;
    this.isRecieved = this.isRecieved ?? false;
  }
}

export function getTransferIdentifier(transfer: ITransfer): number | undefined {
  return transfer.id;
}
