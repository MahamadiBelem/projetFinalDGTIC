import { Moment } from 'moment';
import { IAgrement } from 'app/shared/model/agrement.model';

export interface IArrete {
  id?: number;
  intituleArrete?: string;
  numeroArrete?: string;
  dateSignature?: Moment;
  nombreAgrement?: number;
  agrements?: IAgrement[];
}

export class Arrete implements IArrete {
  constructor(
    public id?: number,
    public intituleArrete?: string,
    public numeroArrete?: string,
    public dateSignature?: Moment,
    public nombreAgrement?: number,
    public agrements?: IAgrement[]
  ) {}
}
