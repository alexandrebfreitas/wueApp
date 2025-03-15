import { IApiassistencia } from 'app/entities/apiassistencia/apiassistencia.model';

export interface IAssistencias {
  id: number;
  value?: string | null;
  apiassistencia?: IApiassistencia | null;
}

export type NewAssistencias = Omit<IAssistencias, 'id'> & { id: null };
