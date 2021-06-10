import { IQualification } from 'app/shared/model/qualification.model';
import { IDomaine } from 'app/shared/model/domaine.model';

export interface ICategorie {
  id?: number;
  codeCategorie?: string;
  libelle?: any;
  qualifications?: IQualification[];
  domaine?: IDomaine;
}

export class Categorie implements ICategorie {
  constructor(
    public id?: number,
    public codeCategorie?: string,
    public libelle?: any,
    public qualifications?: IQualification[],
    public domaine?: IDomaine
  ) {}
}
