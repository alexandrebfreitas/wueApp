export interface IApiindicador {
  id: number;
  filtro?: string | null;
  dadosindicador?: string | null;
  chave?: string | null;
}

export type NewApiindicador = Omit<IApiindicador, 'id'> & { id: null };
