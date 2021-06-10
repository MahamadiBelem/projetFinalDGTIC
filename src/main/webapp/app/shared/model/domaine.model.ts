import { ICategorie } from 'app/shared/model/categorie.model';
import { IQualification } from 'app/shared/model/qualification.model';

export interface IDomaine {
  id?: number;
  codeDomaine?: string;
  libelle?: any;
  categories?: ICategorie[];
  qualifications?: IQualification[];
}

export class Domaine implements IDomaine {
  constructor(
    public id?: number,
    public codeDomaine?: string,
    public libelle?: any,
    public categories?: ICategorie[],
    public qualifications?: IQualification[]
  ) {}
}
