import { ICustomer } from 'app/shared/model/customer.model';

export interface IAddress {
  id?: number;
  buildingNumber?: string;
  streetName?: string;
  neighborhood?: string;
  cityName?: string;
  zipCode?: string;
  additionalNumber?: string;
  regionDescription?: string;
  unitNumber?: string;
  address?: ICustomer;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public buildingNumber?: string,
    public streetName?: string,
    public neighborhood?: string,
    public cityName?: string,
    public zipCode?: string,
    public additionalNumber?: string,
    public regionDescription?: string,
    public unitNumber?: string,
    public address?: ICustomer
  ) {}
}
