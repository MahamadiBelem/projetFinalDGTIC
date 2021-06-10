import { IDomaine } from 'app/shared/model/domaine.model';
import { ICategorie } from 'app/shared/model/categorie.model';
import { IAgrement } from 'app/shared/model/agrement.model';

export interface IQualification {
  id?: number;
  serviceConcerne?: any;
  domaine?: IDomaine;
  categorie?: ICategorie;
  agrements?: IAgrement[];
}

export class Qualification implements IQualification {
  constructor(
    public id?: number,
    public serviceConcerne?: any,
    public domaine?: IDomaine,
    public categorie?: ICategorie,
    public agrements?: IAgrement[]
  ) {}
}
