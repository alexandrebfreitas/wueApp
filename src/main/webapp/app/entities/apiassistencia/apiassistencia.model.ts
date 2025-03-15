export interface IApiassistencia {
  id: number;
  filtro?: string | null;
  dadosassistencia?: string | null;
  chave?: string | null;
}

export type NewApiassistencia = Omit<IApiassistencia, 'id'> & { id: null };
