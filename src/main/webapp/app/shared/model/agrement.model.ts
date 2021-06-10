import { IEntreprise } from 'app/shared/model/entreprise.model';
import { IQualification } from 'app/shared/model/qualification.model';
import { IArrete } from 'app/shared/model/arrete.model';

export interface IAgrement {
  id?: number;
  codeAgrement?: string;
  entreprises?: IEntreprise[];
  qualifications?: IQualification[];
  arrete?: IArrete;
}

export class Agrement implements IAgrement {
  constructor(
    public id?: number,
    public codeAgrement?: string,
    public entreprises?: IEntreprise[],
    public qualifications?: IQualification[],
    public arrete?: IArrete
  ) {}
}
