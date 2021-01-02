import { Moment } from 'moment';

export interface ICustomer {
  id?: number;
  nationalIdentityNumber?: string;
  idType?: string;
  dateOfBirth?: Moment;
  mobilePhoneNumber?: string;
  agentVerificationNumber?: string;
  email?: string;
  language?: string;
  name?: string;
  customerNumber?: string;
  firstNameArabic?: string;
  fatherNameArabic?: string;
  grandFatherNameArabic?: string;
  grandFatherNameEnglish?: string;
  placeOfBirth?: string;
  idIssueDate?: string;
  idExpiryDate?: string;
  maritalStatus?: string;
  customerId?: string;
  profileStatus?: string;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public nationalIdentityNumber?: string,
    public idType?: string,
    public dateOfBirth?: Moment,
    public mobilePhoneNumber?: string,
    public agentVerificationNumber?: string,
    public email?: string,
    public language?: string,
    public name?: string,
    public customerNumber?: string,
    public firstNameArabic?: string,
    public fatherNameArabic?: string,
    public grandFatherNameArabic?: string,
    public grandFatherNameEnglish?: string,
    public placeOfBirth?: string,
    public idIssueDate?: string,
    public idExpiryDate?: string,
    public maritalStatus?: string,
    public customerId?: string,
    public profileStatus?: string
  ) {}
}
