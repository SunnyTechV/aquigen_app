import dayjs from 'dayjs/esm';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { IRmInventory } from 'app/entities/rm-inventory/rm-inventory.model';

export interface IConsumptionDetails {
  id?: number;
  comsumptionDate?: dayjs.Dayjs | null;
  qtyConsumed?: number | null;
  freeField1?: string | null;
  freeField2?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  securityUsers?: ISecurityUser[] | null;
  rmInventory?: IRmInventory | null;
}

export class ConsumptionDetails implements IConsumptionDetails {
  constructor(
    public id?: number,
    public comsumptionDate?: dayjs.Dayjs | null,
    public qtyConsumed?: number | null,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public securityUsers?: ISecurityUser[] | null,
    public rmInventory?: IRmInventory | null
  ) {}
}

export function getConsumptionDetailsIdentifier(consumptionDetails: IConsumptionDetails): number | undefined {
  return consumptionDetails.id;
}
