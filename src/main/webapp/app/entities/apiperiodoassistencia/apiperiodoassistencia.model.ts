export interface IApiperiodoassistencia {
  id: number;
  dadosperiodo?: string | null;
  chave?: string | null;
}

export type NewApiperiodoassistencia = Omit<IApiperiodoassistencia, 'id'> & { id: null };
