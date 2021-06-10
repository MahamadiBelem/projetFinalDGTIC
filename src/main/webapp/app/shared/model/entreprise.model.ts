import { IAgrement } from 'app/shared/model/agrement.model';

export interface IEntreprise {
  id?: number;
  sigleEntreprise?: string;
  nomEntreprise?: any;
  numeroRCCM?: string;
  numeroIFU?: string;
  ville?: string;
  localisation?: string;
  telephone1?: string;
  telephone2?: string;
  agrement?: IAgrement;
}

export class Entreprise implements IEntreprise {
  constructor(
    public id?: number,
    public sigleEntreprise?: string,
    public nomEntreprise?: any,
    public numeroRCCM?: string,
    public numeroIFU?: string,
    public ville?: string,
    public localisation?: string,
    public telephone1?: string,
    public telephone2?: string,
    public agrement?: IAgrement
  ) {}
}
