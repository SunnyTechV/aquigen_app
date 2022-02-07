import dayjs from 'dayjs/esm';
import { IPurchaseOrderDetails } from 'app/entities/purchase-order-details/purchase-order-details.model';
import { IRawMaterialOrder } from 'app/entities/raw-material-order/raw-material-order.model';
import { ICategories } from 'app/entities/categories/categories.model';
import { IUnit } from 'app/entities/unit/unit.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';

export interface IRawMaterialMaster {
  id?: number;
  materialName?: string | null;
  shortName?: string | null;
  chemicalFormula?: string | null;
  hsnNo?: string | null;
  barCode?: string | null;
  qrCode?: string | null;
  gstPercentage?: number | null;
  materialImage?: string | null;
  alertUnits?: string | null;
  casNumber?: string | null;
  catlogNumber?: string | null;
  description?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  isDeleted?: boolean | null;
  isActive?: boolean | null;
  purchaseOrderDetails?: IPurchaseOrderDetails[] | null;
  rawMaterialOrders?: IRawMaterialOrder[] | null;
  categories?: ICategories | null;
  unit?: IUnit | null;
  securityUser?: ISecurityUser | null;
}

export class RawMaterialMaster implements IRawMaterialMaster {
  constructor(
    public id?: number,
    public materialName?: string | null,
    public shortName?: string | null,
    public chemicalFormula?: string | null,
    public hsnNo?: string | null,
    public barCode?: string | null,
    public qrCode?: string | null,
    public gstPercentage?: number | null,
    public materialImage?: string | null,
    public alertUnits?: string | null,
    public casNumber?: string | null,
    public catlogNumber?: string | null,
    public description?: string | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public freeField3?: string | null,
    public freeField4?: string | null,
    public isDeleted?: boolean | null,
    public isActive?: boolean | null,
    public purchaseOrderDetails?: IPurchaseOrderDetails[] | null,
    public rawMaterialOrders?: IRawMaterialOrder[] | null,
    public categories?: ICategories | null,
    public unit?: IUnit | null,
    public securityUser?: ISecurityUser | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
    this.isActive = this.isActive ?? false;
  }
}

export function getRawMaterialMasterIdentifier(rawMaterialMaster: IRawMaterialMaster): number | undefined {
  return rawMaterialMaster.id;
}
