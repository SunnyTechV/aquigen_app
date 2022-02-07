import dayjs from 'dayjs/esm';
import { ICategories } from 'app/entities/categories/categories.model';
import { IUnit } from 'app/entities/unit/unit.model';
import { IQuatationDetails } from 'app/entities/quatation-details/quatation-details.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { IProductInventory } from 'app/entities/product-inventory/product-inventory.model';
import { IProductTransaction } from 'app/entities/product-transaction/product-transaction.model';

export interface IProduct {
  id?: number;
  productName?: string | null;
  alertUnits?: string | null;
  casNumber?: string | null;
  catlogNumber?: string | null;
  molecularWt?: number | null;
  molecularFormula?: string | null;
  chemicalName?: string | null;
  structureImg?: string | null;
  description?: string | null;
  qrCode?: string | null;
  barCode?: string | null;
  gstPercentage?: number | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  categories?: ICategories | null;
  unit?: IUnit | null;
  quatationDetails?: IQuatationDetails | null;
  securityUser?: ISecurityUser | null;
  productInventories?: IProductInventory[] | null;
  productTransactions?: IProductTransaction[] | null;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public productName?: string | null,
    public alertUnits?: string | null,
    public casNumber?: string | null,
    public catlogNumber?: string | null,
    public molecularWt?: number | null,
    public molecularFormula?: string | null,
    public chemicalName?: string | null,
    public structureImg?: string | null,
    public description?: string | null,
    public qrCode?: string | null,
    public barCode?: string | null,
    public gstPercentage?: number | null,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public freeField3?: string | null,
    public freeField4?: string | null,
    public categories?: ICategories | null,
    public unit?: IUnit | null,
    public quatationDetails?: IQuatationDetails | null,
    public securityUser?: ISecurityUser | null,
    public productInventories?: IProductInventory[] | null,
    public productTransactions?: IProductTransaction[] | null
  ) {}
}

export function getProductIdentifier(product: IProduct): number | undefined {
  return product.id;
}
